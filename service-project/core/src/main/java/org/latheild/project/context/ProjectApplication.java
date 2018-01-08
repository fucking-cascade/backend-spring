package org.latheild.project.context;

import org.latheild.common.spring.config.BaseConfiguration;
import org.latheild.common.spring.config.ServiceClientConfiguration;
import org.latheild.common.spring.config.rabbitmq.fanout.FanoutRabbitMQConfig;
import org.latheild.project.dao.ProjectRepository;
import org.latheild.project.dao.ProjectRepositoryImpl;
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
public class ProjectApplication {
    @Bean
    public ProjectRepository projectRepository() {
        return new ProjectRepositoryImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }
}
