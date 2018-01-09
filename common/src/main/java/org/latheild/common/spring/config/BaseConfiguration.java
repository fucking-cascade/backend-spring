package org.latheild.common.spring.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EntityScan(basePackages = {
        "org.latheild.**.domain"
})
@ComponentScan({
        "org.latheild.**.service",
        "org.latheild.**.controller",
        "org.latheild.**.client"
})
@EnableHystrix
public class BaseConfiguration {
    @Bean
    public ApplicationConstant applicationConstant() {
        return new ApplicationConstant();
    }
}
