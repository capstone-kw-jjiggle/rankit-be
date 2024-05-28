package gitbal.backend.schedule;

import gitbal.backend.domain.user.UserService;
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
    private final ScheduleService scheduleService;

    @Scheduled(initialDelay = 10000, fixedRate = 30000)
    public void updateUserScore(){
        //List<String> allUserNames = userService.findAllUserNames();
        // TODO : 임시로 진행하여 임의의 github id 로 등록 진행
        List<String> allUserNames = List.of(userService.findByUserName("khyojun").getNickname(),
            userService.findByUserName("leesj000603").getNickname()
            );
       scheduleService.schedulingUserScore(allUserNames);
    }



    @Scheduled(initialDelay = 10000, fixedRate = 30000)
    public void updateUserCommit(){
        //List<String> allUserNames = userService.findAllUserNames();
        // TODO : 임시로 진행하여 임의의 github id 로 등록 진행
        List<String> allUserNames = List.of(userService.findByUserName("khyojun").getNickname(),
            userService.findByUserName("leesj000603").getNickname()
        );
        scheduleService.schedulingUserCommit(allUserNames);
    }



}
