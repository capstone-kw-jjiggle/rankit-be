package gitbal.backend.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.school.School;
import gitbal.backend.global.constant.Grade;
import gitbal.backend.global.exception.NotFoundRegionException;
import gitbal.backend.global.exception.NotFoundSchoolException;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.exception.UserHasNoRegionException;
import gitbal.backend.global.exception.UserHasNoSchoolException;
import gitbal.backend.global.security.GithubOAuth2UserInfo;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserScoreCalculator userScoreCalculator;
    private final UserInfoService userInfoService;
    private final UserRepository userRepository;
    private final int USER_AROUND_RANGE = 2;




    public Long calculateUserScore(String nickname) {
        try {
            ResponseEntity<String> response = userInfoService.requestUserInfo(nickname);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode dataNode = root.get("data").get("user");
            return delegateToGitbalScore(dataNode);
        } catch (JsonProcessingException e) {
            throw new NotFoundUserException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundUserException();
        }
    }

    private Long delegateToGitbalScore(JsonNode dataNode) {
        return userScoreCalculator.calculate(
            UserScoreInfoDto.of(dataNode.get("pullRequests").get("totalCount").asLong(),
                dataNode.get("contributionsCollection").get("totalCommitContributions").asLong(),
                dataNode.get("issues").get("totalCount").asLong(),
                dataNode.get("followers").get("totalCount").asLong(),
                dataNode.get("repositories").get("totalCount").asLong()));
    }

    public String findUserImgByUsername(String username) {
        return userRepository.findProfileImgByNickname(username)
            .orElseThrow(NotFoundUserException::new);
    }



    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundUserException("게시글 등록 중 사용자를 찾지 못하여 실패하였습니다."));
    }


    public User findByUserName(String username) {
        return userRepository.findByNickname(username).orElseThrow(NotFoundUserException::new);
    }



    public School findSchoolByUserName(String username) {
        User findUser = userRepository.findByNickname(username)
            .orElseThrow(NotFoundUserException::new);

        if(Objects.isNull(findUser.getSchool())){
            throw new UserHasNoSchoolException();
        }

        return findUser.getSchool();
    }

    public Region findRegionByUserName(String username) {
        User findUser = userRepository.findByNickname(username)
            .orElseThrow(NotFoundUserException::new);

        if(Objects.isNull(findUser.getRegion())){
            throw new UserHasNoRegionException();
        }


        return findUser.getRegion();
    }

    public void updateUserSchool(User user, School school) {
        user.setSchool(school);
        userRepository.save(user);
    }

    public void updateUserRegion(User user, Region region) {
        user.setRegion(region);
        userRepository.save(user);
    }

    public UserRegionSchoolNameDto createUserRegionSchoolNameDto(User user) {
        if(isPresentSchool(user) && isPresentRegion(user)) return UserRegionSchoolNameDto.of(user.getRegion().getRegionName(), user.getSchool().getSchoolName());
        if(isPresentSchool(user))  return UserRegionSchoolNameDto.of(null, user.getSchool().getSchoolName());
        if(isPresentRegion(user))  return UserRegionSchoolNameDto.of(user.getRegion().getRegionName(), null);
        return UserRegionSchoolNameDto.of(null, null);
    }

    private boolean isPresentSchool(User user){
        return !Objects.isNull(user.getSchool());
    }

    private boolean isPresentRegion(User user){
        return !Objects.isNull(user.getRegion());
    }


    public void updateUserProfileImg(User user, String imgUrl) {
        user.setProfileImg(imgUrl);
        userRepository.save(user);
    }

    public void deleteUserProfileImg(User user) {
        user.setProfileImg(null);
        userRepository.save(user);
    }

    public List<String> findAllUserNames() {
        return userRepository.findAll().stream().map(u -> u.getNickname()).toList();
    }

    public void updateUserScore(User findUser, Long newScore) {
        findUser.updateScore(newScore);
        updateUserRanking();
    }

    private void updateUserRanking() {
        List<User> users = userRepository.findAll(Sort.by("score").descending());
        int rank = 1;
        for (User user : users) {
            user.setUserRank(rank++);
            userRepository.save(user);
        }
    }

    public MajorLanguageJpaEntity findMajorLanguageByUsername(String username) {
        User findUser = userRepository.findByNickname(username)
            .orElseThrow(NotFoundUserException::new);
        return findUser.getMajorLanguage();
    }

    @Transactional
    public void updateUserRank() {
        List<User> users = userRepository.findAll(Sort.by("score").descending());
        int rank = 1;
        for (User user : users) {
            user.setUserRank(rank++);
        }
    }

    @Transactional
    public void updateUserGrade() {
        List<User> users = userRepository.findAll();
        updateGrade(users);

    }

    @Transactional
    public void updateUser(GithubOAuth2UserInfo githubOAuth2UserInfo) {

        User user = userRepository.findByNickname(githubOAuth2UserInfo.getNickname())
            .orElseThrow(NotFoundUserException::new);

        user.updateImage(githubOAuth2UserInfo);

        userRepository.save(user);
    }

    private void updateGrade(List<User> users) {
        for (User user : users) {
            Long score = user.getScore();

            if (score <= Grade.YELLOW.getUppderBound()) {
                user.setGrade(Grade.YELLOW);
            } else if (score <= Grade.GREEN.getUppderBound()) {
                user.setGrade(Grade.GREEN);
            } else if (score <= Grade.BLUE.getUppderBound()) {
                user.setGrade(Grade.BLUE);
            } else if (score <= Grade.RED.getUppderBound()) {
                user.setGrade(Grade.RED);
            } else if (score <= Grade.GREY.getUppderBound()) {
                user.setGrade(Grade.GREY);
            } else {
                user.setGrade(Grade.PURPLE);
            }
        }
    }

    public List<User> getAllUserExceptCurrentUser(User user) {
        List<User> users = userRepository.findAll();
        users.remove(user);
        return users;
    }

    public Grade getNextGrade(User user) {
        Grade grade = user.getGrade();

        return Grade.nextGrade(grade);
    }

    public int calculateExp(User findUser) {
        Grade nextGrade = getNextGrade(findUser);
        Grade nowUserGrade = findUser.getGrade();

        double nextGradeScore = nextGrade.getUnderBound();
        double nowGradeUnderBound = nowUserGrade.getUnderBound();
        double relativeScore = findUser.getScore() - nowGradeUnderBound;

        double v =  relativeScore / (nextGradeScore - nowGradeUnderBound) * 100.0;
        long round = Math.round(v);
        log.info("round : {}", round);
        return Math.toIntExact(round);
    }





}
