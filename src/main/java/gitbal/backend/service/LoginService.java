package gitbal.backend.service;

import gitbal.backend.domain.GitbalApiDto;
import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.JoinRequestDto;
import gitbal.backend.entity.dto.UserDto;
import gitbal.backend.exception.JoinException;
import gitbal.backend.repository.UserRepository;
import gitbal.backend.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {


    private final SchoolService schoolService;
    private final RegionService regionService;
    private final MajorLanguageService majorLanguageService;
    private final OneDayCommitService oneDayCommitService;
    private final UserService userService;
    private final UserRepository userRepository;


    //TODO: 회원가입을 위한 -> 학교, 지역, 주언어, 커밋 날짜를 넣어야함!
    @Transactional
    public void join(JoinRequestDto joinRequestDto, CustomUserDetails user) {

        String nickname = user.getNickname();
        String avatarUrl = user.getAvatarUrl();

        User findUser = userRepository.findByNickname(nickname)
            .orElseThrow(() -> new JoinException("유저가 존재하지 않습니다."));

        // TODO: 이름 좀 더 생각해보기 -> 이거 승준씨랑 같이 회의때 고민
        GitbalApiDto gitbalApiDto = userService.callUsersGithubApi(nickname);

        //loginRequestDto 학교이름, 지역이름, 프로필 이미지 이름
        UserDto userDto = initUserDto(joinRequestDto, gitbalApiDto, nickname,
            avatarUrl);

        joinUpdate(findUser, userDto);
    }

    private UserDto initUserDto(JoinRequestDto joinRequestDto, GitbalApiDto gitbalApiDto,
        String nickname, String avatarUrl) {
        return UserDto.of(schoolService.findBySchoolName(joinRequestDto.univName()),
            regionService.findByRegionName(joinRequestDto.region()),
            oneDayCommitService.calculateRecentCommit(gitbalApiDto.recentCommit()),
            majorLanguageService.getUserTopLaunguages(nickname),
            nickname,
            gitbalApiDto.score(),
            userService.findUserImg(joinRequestDto.imgName(), avatarUrl)
        );
    }

    private void joinUpdate(User findUser, UserDto userDto) {
        findUser.joinUpdateUser(userDto.school(),
            userDto.region(),
            userDto.oneDayCommit(),
            userDto.majorLanguages(),
            userDto.nickname(),
            userDto.score(),
            userDto.profile_img()
        );
        schoolService.joinNewUserScore(findUser);
        regionService.joinNewUserScore(findUser);
    }


}
