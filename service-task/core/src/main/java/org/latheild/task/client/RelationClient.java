package org.latheild.task.client;

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
    @RequestMapping(value = CHECK_TASK_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkTaskParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = CHECK_TASK_ATTACHMENT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkTaskAttachment(
            @RequestParam(value = "fileId") String fileId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = ADD_TASK_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void addTaskParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = ADD_TASK_ATTACHMENT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void addTaskAttachment(
            @RequestParam(value = "fileId") String fileId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = DELETE_TASK_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void deleteTaskParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = DELETE_TASK_ATTACHMENT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void deleteTaskAttachment(
            @RequestParam(value = "fileId") String fileId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = GET_USER_TASKS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    ArrayList<RelationDTO> getUserTasks(
            @RequestParam(value = "userId") String userId
    );

    @RequestMapping(value = GET_FILE_TASKS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    ArrayList<RelationDTO> getFileTasks(
            @RequestParam(value = "fileId") String fileId
    );
}
