package gitbal.backend.global.config;


import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ScheduleConfig {

    //일단 스레드 1개씩 분리 -> 스케쥴링할 때 비동기적으로 처리하기 위해 사용 서버로 넘어가게 될 경우 스레드의 갯수 제한적이게 되기 때문에!

    @Bean(name = "taskAExecutor")
    public Executor taskAExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1);

        threadPoolTaskExecutor.setThreadNamePrefix("TaskA-");
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }


    @Bean(name = "taskBExecutor")
    public Executor taskBExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(1);

        threadPoolTaskExecutor.setThreadNamePrefix("TaskB-");
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }



}
