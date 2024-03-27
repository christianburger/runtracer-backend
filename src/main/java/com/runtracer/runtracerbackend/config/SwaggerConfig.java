/*
package com.runtracer.runtracerbackend.config; // Assuming this package name

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.WebFluxConfigurer;

//@Configuration
//@Profile("mariadb-flyway-dev")
public class SwaggerConfig implements WebFluxConfigurer {

    @Value("${application.name}")
    private String applicationName;

    @Value("${application.description}")
    private String applicationDescription;

    @Value("${application.version}")
    private String applicationVersion;

    @Value("${springdoc.swagger-ui.path}")
    private String swaggerPath;

    @Bean
    public OpenAPI publicApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(applicationName) // Use injected application name
                        .version(applicationVersion) // Use injected application version
                        .description(applicationDescription));
    }
}
*/
