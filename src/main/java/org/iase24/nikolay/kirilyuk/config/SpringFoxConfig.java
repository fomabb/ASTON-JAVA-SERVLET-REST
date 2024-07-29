package org.iase24.nikolay.kirilyuk.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Nikolay Kirilyuk",
                        email = "fomabb@yandex.by",
                        url = "https://www.linkedin.com/in/nikolay-kirilyuk-a91635232/"
                ), description = "OpenApi documentation",
                title = "OpenApi specification - Aston Trainee",
                version = "1.0",
                license = @License(
                        name = "Backend-developer",
                        url = "https://www.linkedin.com/company/industrial-automation-search-engine/"
                ),
                termsOfService = "https://test.iase24.com"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)
@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
