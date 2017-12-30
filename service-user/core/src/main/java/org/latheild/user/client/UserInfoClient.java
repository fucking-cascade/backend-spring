package org.latheild.user.client;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.userinfo.api.dto.UserInfoDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.latheild.userinfo.api.UserInfoURL.USER_INFO_CREATE_URL;

@FeignClient(name = "user-info-service")
public interface UserInfoClient {
    @RequestMapping(value = USER_INFO_CREATE_URL, method = RequestMethod.POST)
    @ResponseBody
    public UserInfoDTO register(
            @RequestBody RegisterDTO registerDTO
    );
}
