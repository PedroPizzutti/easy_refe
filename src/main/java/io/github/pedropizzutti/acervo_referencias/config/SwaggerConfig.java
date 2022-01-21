package io.github.pedropizzutti.acervo_referencias.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket documentacao() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.github.pedropizzutti.acervo_referencias.rest.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(infoApi());
    }

    private ApiInfo infoApi() {
        return new ApiInfoBuilder()
                .title("Acervo de Referências API")
                .description("API destinada a criar um repositório de referências bibliográficas")
                .version("1.0")
                .contact(contato())
                .build();
    }

    private Contact contato() {
        return new Contact("Pedro Pizzutti",
                "https://github.com/PedroPizzutti",
                "pedropizzutti@gmail.com");
    }
}
