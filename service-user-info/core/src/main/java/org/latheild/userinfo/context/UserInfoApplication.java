package org.latheild.userinfo.context;

import org.latheild.common.spring.config.BaseConfiguration;
import org.latheild.common.spring.config.ServiceClientConfiguration;
import org.latheild.common.spring.config.rabbitmq.fanout.FanoutRabbitMQConfig;
import org.latheild.userinfo.dao.UserInfoRepository;
import org.latheild.userinfo.dao.UserInfoRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        BaseConfiguration.class,
        ServiceClientConfiguration.class,
        FanoutRabbitMQConfig.class
})
@Configuration
public class UserInfoApplication {
    @Bean
    public UserInfoRepository userInfoRepository() {
        return new UserInfoRepositoryImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserInfoApplication.class, args);
    }
}
