package org.latheild.subtask.context;

import org.latheild.common.spring.config.BaseConfiguration;
import org.latheild.common.spring.config.ServiceClientConfiguration;
import org.latheild.common.spring.config.rabbitmq.fanout.FanoutRabbitMQConfig;
import org.latheild.subtask.dao.SubtaskRepository;
import org.latheild.subtask.dao.SubtaskRepositoryImpl;
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
public class SubtaskApplication {
    @Bean
    public SubtaskRepository subtaskRepository() {
        return new SubtaskRepositoryImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(SubtaskApplication.class, args);
    }
}
