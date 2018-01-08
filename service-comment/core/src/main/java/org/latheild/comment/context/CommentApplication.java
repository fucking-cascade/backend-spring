package org.latheild.comment.context;

import org.latheild.comment.dao.CommentRepository;
import org.latheild.comment.dao.CommentRepositoryImpl;
import org.latheild.common.spring.config.BaseConfiguration;
import org.latheild.common.spring.config.ServiceClientConfiguration;
import org.latheild.common.spring.config.rabbitmq.fanout.FanoutRabbitMQConfig;
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
public class CommentApplication {
    @Bean
    public CommentRepository commentRepository() {
        return new CommentRepositoryImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(CommentApplication.class, args);
    }
}
