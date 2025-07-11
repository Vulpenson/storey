package com.fox.storey.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Storey API")
                        .version("1.0")
                        .description("API for managing the Storey backend."))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

    @Bean
    public OpenApiCustomizer secureEndpointCustomizer() {
        return openApi -> openApi.getPaths().forEach((path, pathItem) -> {
            boolean isSecure = path.startsWith("/storey/")
                    || (path.startsWith("/auth/") && !path.equals("/auth/generateToken"));

            if (isSecure) {
                pathItem.readOperations().forEach(operation ->
                        operation.addSecurityItem(
                                new SecurityRequirement().addList(SECURITY_SCHEME_NAME)
                        )
                );
            }
        });
    }
}