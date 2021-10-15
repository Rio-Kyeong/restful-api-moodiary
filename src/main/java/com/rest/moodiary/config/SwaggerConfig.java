package com.rest.moodiary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Contact DEFAULT_CONTACT =  new Contact("Ryu Kyeong Woo",
            "https://github.com/RyuKyeongWoo", "dtg9811@gmail.com");

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
            Arrays.asList("application/json","application/xml"));

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfoBuilder()
        .title("Moodiary API")
        .description("Mood Diary Management REST API Service")
        .version("1.0")
        .contact(DEFAULT_CONTACT)
        .build();

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rest.moodiary.controller"))
                .build()
                .apiInfo(DEFAULT_API_INFO)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}
