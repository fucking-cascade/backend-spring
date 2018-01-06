package org.latheild.user.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.task.api.constant.TaskURL.CHECK_TASK_EXIST_URL;
import static org.latheild.task.api.constant.TaskURL.GET_PROJECT_ID_URL;

@FeignClient(name = "task-service")
public interface TaskClient {
    @RequestMapping(value = GET_PROJECT_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    String getProjectId(
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = CHECK_TASK_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkTaskExistence(
            @RequestParam(value = "taskId") String taskId
    );
}
