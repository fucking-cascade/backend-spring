package org.latheild.comment.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.task.api.constant.TaskURL.CHECK_TASK_EXIST_URL;

@FeignClient(name = "task-service")
public interface TaskClient {
    @RequestMapping(value = CHECK_TASK_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkTaskExistence(
            @RequestParam(value = "taskId") String taskId
    );
}
