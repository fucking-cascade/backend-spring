package org.latheild.progress.context;

import org.latheild.common.spring.config.BaseConfiguration;
import org.latheild.common.spring.config.ServiceClientConfiguration;
import org.latheild.common.spring.config.rabbitmq.fanout.FanoutRabbitMQConfig;
import org.latheild.progress.dao.ProgressRepository;
import org.latheild.progress.dao.ProgressRepositoryImpl;
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
public class ProgressApplication {
    @Bean
    public ProgressRepository progressRepository() {
        return new ProgressRepositoryImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProgressApplication.class, args);
    }
}
