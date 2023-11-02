package com.ophis.projectx.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Euder de Sousa",
                        email = "euderdesousaaaa@gmail.com",
                        url = "https://github.com/euderdesousaa"
                ),
                description = "OpenApi documentation for social network project",
                title = "ProjectX API- Euder",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://api-projectx.onrender.com/"
                )
        }

)

public class OpenApiConfig {
}
