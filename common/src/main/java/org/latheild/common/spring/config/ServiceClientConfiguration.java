package org.latheild.common.spring.config;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableEurekaClient
@EnableFeignClients({
        "org.latheild.**.client"
})
@EnableWebMvc
public class ServiceClientConfiguration {
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
