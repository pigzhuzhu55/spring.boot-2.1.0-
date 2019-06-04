package com.wolf.dearcc.manager;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,
        scanBasePackages = {
                "com.wolf.dearcc.manager",
                "com.wolf.dearcc.service",
                "com.wolf.dearcc.dao",
        })
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class DearccManagerWebApplication {

    private static final Logger logger = LoggerFactory.getLogger(DearccManagerWebApplication.class);

    public static void main(String[] args) {
        logger.info("SpringBoot开始加载");
        SpringApplication.run(DearccManagerWebApplication.class, args);
        logger.info("SpringBoot加载完毕");
    }

    //如果采用注解这种方式，感觉都可以不用放在这个地方
    //只要在spring容器启动的时候被扫描到就行了
    @Bean
    public HttpMessageConverters fastJsonConfigure(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //日期格式化
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(converter);
    }
}
