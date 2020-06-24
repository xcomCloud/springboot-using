package com.xue.study.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Author: mf015
 * Date: 2020/5/5 0005
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConf {
    @Bean(value = "createApi")
    public Docket createApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                //
                .apiInfo(apiInfo())
                //调用接口后会和paths拼接在一起
                .pathMapping("/")
                //
                .select()
                //包路径
                .apis(RequestHandlerSelectors.basePackage("com.xue.study.controller"))
                //过滤的接口
                .paths(PathSelectors.any())
                //
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("knife4j-using服务的API接口")
                .description("### knife4j demo ###")
                .termsOfServiceUrl("http://localhost:6666/")
                .version("1.0")
                .build();
    }
}
