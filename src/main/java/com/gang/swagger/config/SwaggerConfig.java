package com.gang.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @ProjectName : swagger-demo
 * @作者 : 侯小刚
 * @描述 :
 * @创建日期 : 2020-09-28 11:11
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket docket1() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("分组1");
    }

    @Bean
    public Docket docket2() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("分组2");
    }

    @Bean
    public Docket docket3() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("分组3");
    }

    //配置了Swagger的Dockey的bean实例
    @Bean
    public Docket docket(Environment environment) {
        Profiles profiles = Profiles.of("dev","test");
        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否启用Swagger,false则浏览器中不能访问
                .enable(flag)
                .select()
                //RequestHandlerSelectors,配置要扫描的接口的方式
                //.apis(RequestHandlerSelectors.basePackage("com.gang.swagger.controller"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();
    }

    //配置Swagger信息 = apiInfo
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("小刚","https://www.baidu.com","952551626@qq.com");
        return new ApiInfo(
                "小刚的SwaggerAPI文档",
                "村里来的",
                "v1.0",
                "https://www.baidu.com",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }

}
