package kr.hanjari.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );

        List<Server> serverList = new ArrayList<>();
        Server httpsDevServer = new Server();
        httpsDevServer.setUrl("https://hanjari-dev.site");
        httpsDevServer.setDescription("Hanjari Dev Https Server");
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Hanjari Local Server");

        serverList.add(httpsDevServer);
        serverList.add(localServer);

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .servers(serverList)
                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("Hanjari Backend API")
                .description("Hanjari Backend API")
                .version("1.0.0");
    }

}
