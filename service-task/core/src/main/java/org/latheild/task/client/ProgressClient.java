package org.latheild.task.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.progress.api.constant.ProgressURL.CHECK_PROGRESS_EXIST_URL;

@FeignClient(name = "progress-service")
public interface ProgressClient {
    @RequestMapping(value = CHECK_PROGRESS_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkProgressExistence(
            @RequestParam(value = "progressId") String progressId
    );
}
