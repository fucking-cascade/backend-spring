package org.latheild.user.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.schedule.api.constant.ScheduleURL.CHECK_SCHEDULE_EXIST_URL;
import static org.latheild.schedule.api.constant.ScheduleURL.GET_PROJECT_ID_URL;

@FeignClient(name = "schedule-service")
public interface ScheduleClient {
    @RequestMapping(value = GET_PROJECT_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    String getProjectId(
            @RequestParam(value = "scheduleId") String scheduleId
    );

    @RequestMapping(value = CHECK_SCHEDULE_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkScheduleExistence(
            @RequestParam(value = "scheduleId") String scheduleId
    );
}
