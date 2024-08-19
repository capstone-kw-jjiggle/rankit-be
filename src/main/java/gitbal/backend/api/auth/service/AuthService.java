package gitbal.backend.api.auth.service;

import gitbal.backend.api.auth.dto.JoinRequestDto;
import gitbal.backend.api.auth.dto.UserDto;
import gitbal.backend.domain.refreshtoken.application.RefreshTokenService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.domain.majorlanguage.application.MajorLanguageService;
import gitbal.backend.domain.onedaycommit.OneDayCommitService;
import gitbal.backend.domain.region.application.RegionService;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.exception.JoinException;
import gitbal.backend.global.exception.LogoutException;
import gitbal.backend.global.exception.NotDrawUserException;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.security.CustomUserDetails;
import gitbal.backend.global.util.AuthenticationChecker;
import gitbal.backend.api.auth.dto.GitbalApiDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final SchoolService schoolService;
    private final RegionService regionService;
    private final MajorLanguageService majorLanguageService;
    private final OneDayCommitService oneDayCommitService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationChecker authenticationChecker;
    private final RefreshTokenService refreshTokenService;


    @Transactional
    public void join(JoinRequestDto joinRequestDto, CustomUserDetails user) {

        String nickname = user.getNickname();

        User findUser = userRepository.findByNickname(nickname)
            .orElseThrow(() -> new JoinException("유저가 존재하지 않습니다."));

        // TODO: 이름 좀 더 생각해보기 -> 이거 승준씨랑 같이 회의때 고민
        GitbalApiDto gitbalApiDto = GitbalApiDto.of(userService.calculateUserScore(nickname),
            userService.checkUserRecentCommit(nickname));

        //loginRequestDto 학교이름, 지역이름, 프로필 이미지 이름
        UserDto userDto = initUserDto(joinRequestDto, gitbalApiDto, nickname);

        joinUpdate(findUser, userDto);
        updateRank();
    }


    private UserDto initUserDto(JoinRequestDto joinRequestDto, GitbalApiDto gitbalApiDto,
        String nickname) {
        return UserDto.of(schoolService.findBySchoolName(joinRequestDto.univName()),
            regionService.findByRegionName(joinRequestDto.region()),
            oneDayCommitService.calculateRecentCommit(gitbalApiDto.getRecentCommit()),
            majorLanguageService.getUserTopLaunguage(nickname),
            nickname,
            gitbalApiDto.getScore(),
            userService.findUserImgByUsername(nickname)
        );
    }


    @Transactional
    public String withDrawUser(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        try {
            userRepository.delete(user);
            return "성공적으로 탈퇴 처리 되었습니다.";
        } catch (Exception e) {
            throw new NotDrawUserException();
        }
    }


    private void joinUpdate(User findUser, UserDto userDto) {
        findUser.joinUpdateUser(userDto.school(),
            userDto.region(),
            userDto.oneDayCommitJpaEntity(),
            userDto.majorLanguage(),
            userDto.nickname(),
            userDto.score(),
            userDto.profile_img(),
            0
        );
        schoolService.joinNewUserScore(findUser);
        regionService.joinNewUserScore(findUser);
    }


    private User getAuthenticatedUser(Authentication authentication) {
        String username = authenticationChecker.checkAndRetrieveNickname(authentication);
        return userRepository.findByNickname(username).
            orElseThrow(NotFoundUserException::new);
    }


    @Transactional
    public String logoutUser(String username) {
        try {
            refreshTokenService.deleteByUsername(username);
            return "로그아웃에 성공하였습니다.";
        }catch (Exception e){
            e.printStackTrace();
            throw new LogoutException("로그아웃 실패");
        }
    }

    private void updateRank() {
        userService.updateUserRank();
        userService.updateUserGrade();
        schoolService.updateSchoolGrade();
        schoolService.updateSchoolRank();
    }
}
