package org.latheild.schedule.client;

import org.latheild.common.api.CommonIdentityType;
import org.latheild.relation.api.dto.RelationDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.relation.api.constant.RelationURL.*;

@FeignClient(name = "relation-service")
public interface RelationClient {
    @RequestMapping(value = CHECK_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkScheduleParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "scheduleId") String scheduleId
    );

    @RequestMapping(value = ADD_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void addScheduleParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "scheduleId") String scheduleId
    );

    @RequestMapping(value = DELETE_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void deleteScheduleParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "scheduleId") String scheduleId
    );

    @RequestMapping(value = GET_USER_SCHEDULES_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    ArrayList<RelationDTO> getUserSchedules(
            @RequestParam(value = "userId") String userId
    );

    @RequestMapping(value = GET_MEMBER_IDENTITY_OF_PROJECT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    CommonIdentityType getMemberIdentityOfProject(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    );
}
