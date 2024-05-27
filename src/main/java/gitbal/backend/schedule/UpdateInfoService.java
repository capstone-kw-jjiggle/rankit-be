package gitbal.backend.schedule;

import gitbal.backend.api.auth.dto.GitbalApiDto;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.RegionService;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateInfoService {

    private final UserService userService;
    private final SchoolService schoolService;
    private final RegionService regionService;

    @Transactional
    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void updateUserScore(){
        //List<String> allUserNames = userService.findAllUserNames();
        // TODO : 임시로 진행하여 임의의 github id 로 등록 진행
        List<String> allUserNames = List.of(userService.findByUserName("khyojun").getNickname(),
            userService.findByUserName("leesj000603").getNickname()
            );
        for (String username : allUserNames) {
            GitbalApiDto gitbalApiDto = userService.callUsersGithubApi(username);
            User findUser = userService.findByUserName(username);
            Long oldScore = findUser.getScore();
            Long newScore = gitbalApiDto.getScore();
            if(oldScore.equals(newScore))
                continue;
            School school = findUser.getSchool();
            Region region = findUser.getRegion();
            schoolService.updateSchool(school, username,oldScore, newScore);
            regionService.updateRegion(region,username,oldScore,newScore);
            userService.updateUserScore(findUser, newScore);
            userService.updateCommit(findUser, gitbalApiDto.getRecentCommit());
        }
    }


    @Transactional
    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void updateUserCommitDate(){
        //List<String> allUserNames = userService.findAllUserNames();
        // TODO : 임시로 진행하여 임의의 github id 로 등록 진행
        List<String> allUserNames = List.of(userService.findByUserName("khyojun").getNickname(),
            userService.findByUserName("leesj000603").getNickname()
        );
        for (String username : allUserNames) {
            GitbalApiDto gitbalApiDto = userService.callUsersGithubApi(username);
            User findUser = userService.findByUserName(username);
            Long oldScore = findUser.getScore();
            Long newScore = gitbalApiDto.getScore();
            if(oldScore.equals(newScore))
                continue;
            School school = findUser.getSchool();
            Region region = findUser.getRegion();
            schoolService.updateSchool(school, username,oldScore, newScore);
            regionService.updateRegion(region,username,oldScore,newScore);
            userService.updateUserScore(findUser, newScore);
        }
    }


}
