package com.citymanager.Project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.citymanager.Project"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "City Manager - Projects API",
                "API Desafio da academia Java MicroServiços.",
                "API TOS",
                "Terms of service - Libre",
                new Contact("Dalmo Felipe Torres de Paula", "https://www.github.com/dalmofelipe", "dfelipe@outlook.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
