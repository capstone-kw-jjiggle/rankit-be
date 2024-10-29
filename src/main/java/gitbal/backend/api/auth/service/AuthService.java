package gitbal.backend.api.auth.service;

import gitbal.backend.api.auth.dto.JoinRequestDto;
import gitbal.backend.api.auth.dto.UserDto;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.domain.majorlanguage.application.MajorLanguageService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    private final SchoolService schoolService;
    private final RegionService regionService;
    private final MajorLanguageService majorLanguageService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationChecker authenticationChecker;


    @Transactional
    public void join(JoinRequestDto joinRequestDto, CustomUserDetails user) {

        String nickname = user.getNickname();

        User findUser = userRepository.findByNickname(nickname)
            .orElseThrow(() -> new JoinException("유저가 존재하지 않습니다."));
        if(!findUser.getFirstLogined())
            findUser.toggleLogined();
        GitbalApiDto gitbalApiDto = GitbalApiDto.of(userService.calculateUserScore(nickname));

        //loginRequestDto 학교이름, 지역이름, 프로필 이미지 이름
        UserDto userDto = initUserDto(joinRequestDto, gitbalApiDto, nickname);
        joinUpdate(findUser, userDto);
        updateRank();
    }


    private UserDto initUserDto(JoinRequestDto joinRequestDto, GitbalApiDto gitbalApiDto,
        String nickname) {
        return UserDto.of(schoolService.findBySchoolName(joinRequestDto.univName()),
            regionService.findByRegionName(joinRequestDto.region()),
            majorLanguageService.getUserTopLaunguage(nickname).getMajorLanguage(),
            nickname,
            gitbalApiDto.getScore(),
            userService.findUserImgByUsername(nickname),
            userService.findByUserName(nickname).getIntroduction()
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
            userDto.majorLanguage(),
            userDto.nickname(),
            userDto.score(),
            userDto.profile_img(),
            0,
            userDto.introduction()
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
            User user = userRepository.findByNickname(username)
                .orElseThrow(NotFoundUserException::new);
            user.setRefreshToken("nothing");
            log.info("로그아웃 성공");
            return "로그아웃에 성공하였습니다.";
        }catch (Exception e){
            e.printStackTrace();
            throw new LogoutException("로그아웃 실패");
        }
    }

    private void updateRank() {
        userService.updateUserRank();
        userService.updateUserGrade();
        schoolService.updateSchoolRank();
    }
}
