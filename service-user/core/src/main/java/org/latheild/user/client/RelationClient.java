package org.latheild.user.client;

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
    @RequestMapping(value = CHECK_PROJECT_MEMBER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkProjectMember(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    );

    @RequestMapping(value = CHECK_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkScheduleParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "scheduleId") String scheduleId
    );

    @RequestMapping(value = CHECK_TASK_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkTaskParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = ADD_PROJECT_MEMBER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void addProjectMember(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "identityType") CommonIdentityType identityType
    );

    @RequestMapping(value = GET_PROJECT_MEMBERS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    ArrayList<RelationDTO> getProjectMembers(
            @RequestParam(value = "projectId") String projectId
    );

    @RequestMapping(value = GET_SCHEDULE_PARTICIPANTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    ArrayList<RelationDTO> getScheduleParticipants(
            @RequestParam(value = "scheduleId") String scheduleId
    );

    @RequestMapping(value = GET_TASK_PARTICIPANTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    ArrayList<RelationDTO> getTaskParticipants(
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = ADD_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void addScheduleParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "scheduleId") String scheduleId
    );

    @RequestMapping(value = ADD_TASK_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void addTaskParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = DELETE_PROJECT_MEMBER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void deleteProjectMember(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    );

    @RequestMapping(value = DELETE_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void deleteScheduleParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "scheduleId") String scheduleId
    );

    @RequestMapping(value = DELETE_TASK_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void deleteTaskParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = GET_MEMBER_IDENTITY_OF_PROJECT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    CommonIdentityType getMemberIdentityOfProject(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    );
}
