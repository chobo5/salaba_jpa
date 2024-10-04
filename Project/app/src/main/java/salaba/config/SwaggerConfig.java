package salaba.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class SwaggerConfig {
    private static final String SERVICE_NAME = "Salaba";
    private static final String API_VERSION = "V1";
    private static final String API_DESCRIPTION = "Salaba Restful API";
    private static final String API_URL = "http://localhost:8888";
    private static final String TOKEN = "Bearer Token";

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(TOKEN, apiKey()))
                .info(apiInfo())
                .addSecurityItem(securityRequirement());
    }

    private Info apiInfo() {
        return new Info()
                .title(SERVICE_NAME)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .termsOfService(API_URL);
    }

    private SecurityScheme apiKey() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .scheme("bearer")
                .bearerFormat("JWT");
    }


    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement()
                .addList("Bearer Token");
    }

}
