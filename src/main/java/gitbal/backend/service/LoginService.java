package gitbal.backend.service;

import gitbal.backend.domain.User;
import gitbal.backend.dto.JoinRequestDto;
import gitbal.backend.dto.UserDto;
import gitbal.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    public void join(String nickname, JoinRequestDto joinRequestDto) {


        //loginRequestDto 학교이름, 지역이름, 프로필 이미지 이름
        User user = userService.findByNickname(nickname);



        UserDto.of(schoolService.findBySchoolName(joinRequestDto.univName()),
            regionService.findByRegionName(joinRequestDto.region()),
            commitDateService.calculateRecentCommit(nickname),
            majorLanguageService.saveUserMajorLanguage(nickname),
            nickname,
            userService.calculatePrCount(),
            userService.calculateCommitCount(),
            userService.findUserImg(nickname)
            );



    }
}
