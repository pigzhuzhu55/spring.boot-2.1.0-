package com.wolf.dearcc.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;

@SpringBootApplication
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class DearccManagerWebApplication {

    private static final Logger logger = LoggerFactory.getLogger(DearccManagerWebApplication.class);

    public static void main(String[] args) {
        logger.info("SpringBoot开始加载");
        SpringApplication.run(DearccManagerWebApplication.class, args);
        logger.info("SpringBoot加载完毕");
    }

}
