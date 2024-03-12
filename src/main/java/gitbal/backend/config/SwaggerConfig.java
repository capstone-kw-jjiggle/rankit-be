package gitbal.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi(){
        return new OpenAPI()
            .components(new Components())
            .info(apiInfo());

    }

    private Info apiInfo() {
        return new Info()
            .title("깃발 API 명세서")
            .description("GitBal API 명세서입니다.")
            .version("1.0.0");
    }


}
