package gitbal.backend.service;

import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.JoinRequestDto;
import gitbal.backend.entity.dto.UserDto;
import gitbal.backend.exception.JoinException;
import gitbal.backend.repository.UserRepository;
import gitbal.backend.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {


    private final SchoolService schoolService;
    private final RegionService regionService;
    private final MajorLanguageService majorLanguageService;
    private final CommitDateService commitDateService;
    private final UserService userService;

    private final UserRepository userRepository;


    //TODO: 회원가입을 위한 -> 학교, 지역, 주언어, 커밋 날짜를 넣어야함!
    @Transactional
    public void join(JoinRequestDto joinRequestDto, CustomUserDetails user) {

        String nickname = user.getNickname();
        String avatarUrl = user.getAvatarUrl();

        User findUser = userRepository.findByNickname(nickname)
            .orElseThrow(() -> new JoinException("유저가 존재하지 않습니다."));

        //loginRequestDto 학교이름, 지역이름, 프로필 이미지 이름
        UserDto userDto = UserDto.of(schoolService.findBySchoolName(joinRequestDto.univName()),
            regionService.findByRegionName(joinRequestDto.region()),
            commitDateService.calculateRecentCommit(nickname),
            majorLanguageService.getUserTopLaunguages(nickname),
            nickname,
            userService.getUserInformation(nickname).prCount(),
            userService.getUserInformation(nickname).commitCount(),
            userService.findUserImg(joinRequestDto.imgName(), avatarUrl)
        );

        findUser.joinUpdateUser(userDto.school(),
            userDto.region(),
            userDto.commitDate(),
            userDto.majorLanguages(),
            userDto.nickname(),
            userDto.pr_count(),
            userDto.commit_count(),
            userDto.profile_img()
            );

    }


}
