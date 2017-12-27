package org.latheild.common.spring.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EntityScan(basePackages = {
        "org.latheild.**.domain"
})
@ComponentScan({
        "org.latheild.**.service",
        "org.latheild.**.controller"
})
@EnableHystrix
@EnableMongoAuditing
public class BaseConfiguration {
    @Bean
    public ApplicationConstant applicationConstant() {
        return new ApplicationConstant();
    }
}
