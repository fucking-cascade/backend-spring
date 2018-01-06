package org.latheild.user.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.project.api.constant.ProjectURL.CHECK_PROJECT_EXIST_URL;

@FeignClient(name = "project-service")
public interface ProjectClient {
    @RequestMapping(value = CHECK_PROJECT_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkProjectExistence(
            @RequestParam(value = "projectId") String projectId
    );
}
