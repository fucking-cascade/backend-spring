package org.latheild.relation.core.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.user.api.constant.UserUrl.CHECK_USER_EXIST_URL;

@FeignClient(name = "user-service")
public interface UserClient {
    @RequestMapping(value = CHECK_USER_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkUserExistence(
            @RequestParam(value = "userId") String userId
    );
}
