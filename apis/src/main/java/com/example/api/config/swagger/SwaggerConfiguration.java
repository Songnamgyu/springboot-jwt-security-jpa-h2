package com.example.api.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	private String version;


    @Bean
    public Docket swaggerApi() {
    	
    	version = "Aiden-V1";
    	
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.api"))
                .paths(PathSelectors.ant("/v1/**"))
                .build()
                .useDefaultResponseMessages(false)
                .groupName(version); 
    }
   
    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Spring API Documentation(로그인, 회원가입, 게시판)")
                .description("SpringBoot에서 Security-JwtToken을 이용한 JPA적용")
                .license("회원가입, 로그인, 게시판 관련 API").licenseUrl("http://localhost:8002/swagger-ui.html").build();
    }
}
