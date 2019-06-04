package com.wolf.dearcc.manager;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean(value = "defaultApi")
    @Order(value = 1)
    public Docket defaultApi() {
        List<Parameter> parameters= Lists.newArrayList();
        parameters.add(new ParameterBuilder()
                .name("ssid").description("用户标识").modelRef(new ModelRef("String"))
                .parameterType("header").defaultValue("0").required(true)
                .build());
        parameters.add(new ParameterBuilder()
                .name("orgid").description("系统标识").modelRef(new ModelRef("String"))
                .parameterType("header").defaultValue("0").required(true)
                .build());
        parameters.add(new ParameterBuilder()
                .name("timestamp").description("10位时间戳").modelRef(new ModelRef("String"))
                .parameterType("header").defaultValue("1234567890").required(true)
                .build());
        parameters.add(new ParameterBuilder()
                .name("authkey").description("md5校验key").modelRef(new ModelRef("String"))
                .parameterType("header").defaultValue("test").required(true)
                .build());
        parameters.add(new ParameterBuilder()
                .name("token").description("登陆token").modelRef(new ModelRef("String"))
                .parameterType("header").defaultValue("0").required(true)
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("2、业务接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wolf.dearcc.manager.controller.v1"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameters)

                ;

    }

    @Bean(value = "loginApi")
    @Order(value = 2)
    public Docket loginApi() {
        List<Parameter> parameters= Lists.newArrayList();
        parameters.add(new ParameterBuilder()
                .name("timestamp").description("10位时间戳").modelRef(new ModelRef("String"))
                .parameterType("header").defaultValue("1234567890").required(true)
                .build());
        parameters.add(new ParameterBuilder()
                .name("authkey").description("md5校验key").modelRef(new ModelRef("String"))
                .parameterType("header").defaultValue("test").required(true)
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("1、通用接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wolf.dearcc.manager.controller.none"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameters)
                ;

    }


    //Swagger中过滤掉任意API接口的方法 TODO 不想研究了
    //http://www.daxiblog.com/2018/07/25/swagger%E4%B8%AD%E8%BF%87%E6%BB%A4%E6%8E%89%E4%BB%BB%E6%84%8Fapi%E6%8E%A5%E5%8F%A3%E7%9A%84%E6%96%B9%E6%B3%95/

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(" 服务端通用接口文档 RESTful APIs")
                .description("  ^_^  ")
                .termsOfServiceUrl("")
                .contact("服务端")
                .version("1.0")
                .build();
    }
}
