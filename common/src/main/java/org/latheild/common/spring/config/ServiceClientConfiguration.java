package org.latheild.common.spring.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableEurekaClient
@EnableFeignClients({
        "org.latheild.**.client"
})
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableWebMvc
public class ServiceClientConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
//    @Bean(destroyMethod = "shutdown")
//    @org.springframework.cloud.context.config.annotation.RefreshScope
//    public EurekaClient eurekaClient(
//            ApplicationInfoManager manager,
//            EurekaClientConfig config,
//            DiscoveryClient.DiscoveryClientOptionalArgs optionalArgs,
//            ApplicationContext context
//    ) {
//        manager.getInfo();
//        return new CloudEurekaClient(manager, config, optionalArgs, context);
//    }
}
