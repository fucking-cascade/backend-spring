package org.latheild.schedule.context;

import org.latheild.common.spring.config.BaseConfiguration;
import org.latheild.common.spring.config.ServiceClientConfiguration;
import org.latheild.common.spring.config.rabbitmq.fanout.FanoutRabbitMQConfig;
import org.latheild.schedule.dao.ScheduleRepository;
import org.latheild.schedule.dao.ScheduleRepositoryImpl;
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
public class ScheduleApplication {
    @Bean
    public ScheduleRepository scheduleRepository() {
        return new ScheduleRepositoryImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(ScheduleApplication.class, args);
    }
}
