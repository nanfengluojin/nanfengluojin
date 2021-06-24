package com.wzp.nflj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class NanfengluojinApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanfengluojinApplication.class, args);
    }

    //springboot1.4版本以后需要在启动器里自定义RestTemplate,即在启动器中加入如下代码即可在类中正常使用@Autowired进行注入
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
