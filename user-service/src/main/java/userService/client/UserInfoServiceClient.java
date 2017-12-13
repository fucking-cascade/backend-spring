package userService.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import userService.domain.Gender;
import userService.domain.UserInfoWrapper;

@FeignClient(name = "user-info-service")
public interface UserInfoServiceClient {
    @RequestMapping(value = "/createUserInfo/v3", method = RequestMethod.POST)
    public Object createUserInfoV3(
            @RequestBody UserInfoWrapper userInfoWrapper
    );

    @RequestMapping(value = "/deleteUserInfo/test", method = RequestMethod.GET)
    public void deleteUserInfo();
}