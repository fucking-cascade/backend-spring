package org.latheild.task.context;

import org.latheild.common.spring.config.BaseConfiguration;
import org.latheild.common.spring.config.ServiceClientConfiguration;
import org.latheild.common.spring.config.rabbitmq.fanout.FanoutRabbitMQConfig;
import org.latheild.task.dao.TaskRepository;
import org.latheild.task.dao.TaskRepositoryImpl;
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
public class TaskApplication {
    @Bean
    public TaskRepository taskRepository() {
        return new TaskRepositoryImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
