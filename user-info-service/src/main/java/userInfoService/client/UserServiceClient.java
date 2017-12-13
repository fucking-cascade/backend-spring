package userInfoService.client;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "user-service")
public interface UserServiceClient {
}
