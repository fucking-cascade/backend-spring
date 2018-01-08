package org.latheild.relation.core.context;

import org.latheild.common.spring.config.BaseConfiguration;
import org.latheild.common.spring.config.ServiceClientConfiguration;
import org.latheild.common.spring.config.rabbitmq.fanout.FanoutRabbitMQConfig;
import org.latheild.relation.core.dao.*;
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
public class RelationApplication {
    @Bean
    public FileTaskRelationRepository fileTaskRelationRepository() {
        return new FileTaskRelationRepositoryImpl();
    }

    @Bean
    public UserProjectRelationRepository userProjectRelationRepository() {
        return new UserProjectRelationRepositoryImpl();
    }

    @Bean
    public UserScheduleRelationRepository userScheduleRelationRepository() {
        return new UserScheduleRelationRepositoryImpl();
    }

    @Bean
    public UserTaskRelationRepository userTaskRelationRepository() {
        return new UserTaskRelationRepositoryImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(RelationApplication.class, args);
    }
}
