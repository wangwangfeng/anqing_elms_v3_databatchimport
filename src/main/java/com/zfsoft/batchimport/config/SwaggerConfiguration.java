package com.zfsoft.batchimport.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description: Swagger配置
 * @ClassName: SwaggerConfiguration
 * @author kkfan
 * @date 2018年12月23日 下午8:30:04
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi(@Value("${project.controller.package}") String controllerPackage) {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(
                RequestHandlerSelectors
                    .basePackage(controllerPackage))
            .paths(PathSelectors.any()).build();
    }

    @SuppressWarnings("deprecation")
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("swagger-bootstrap-ui RESTful APIs")
            .description("swagger-bootstrap-ui")
            .termsOfServiceUrl("fkk135@foxmail.com")
            .contact("fkk135@foxmail.com").version("1.8.8").build();
    }

}