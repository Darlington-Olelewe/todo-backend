package com.todo.todobackend.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

    private static  final String AUTHORIZATION_HEADER = "Authorization";
    private  ApiKey apikey(){
        return  new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.todo.todobackend"))
                .build()
                .apiInfo(apiDetails())
                .securitySchemes(asList(apikey()))
                .securityContexts(asList(securityContext()));
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "Todo Backed",
                "Simple Todo Application",
                "2.0",
                "this version is in-house",
                new springfox.documentation.service.Contact("Angular Todo Application",
                        "http://http:",
                        "olelewexpressrules@gmail.com"),
                "Api License",
                "http://",
                Collections.emptyList()
        );
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "totalAccess");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return new ArrayList(asList(new SecurityReference("JWT", authorizationScopes)));
    }
}
