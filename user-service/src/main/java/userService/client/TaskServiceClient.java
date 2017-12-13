package userService.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("task-service")
public interface TaskServiceClient {
    @RequestMapping(value = "/createTutorial/v1", method = RequestMethod.POST)
    @ResponseBody
    public int createTutorialV1(
            @RequestBody Integer ownerId
    );
}
