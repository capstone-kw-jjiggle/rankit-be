package gitbal.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@EnableSchedulerLock(defaultLockAtMostFor = "PT2M")
@OpenAPIDefinition(servers = {
	@Server(url = "https://api.rankit.run", description = "deploy Server URL - https 인 상태로 보내야합니다"),
	@Server(url = "http://localhost:8080", description = "Localhost URL")
})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
