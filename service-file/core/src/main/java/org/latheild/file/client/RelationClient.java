package org.latheild.file.client;

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
    @RequestMapping(value = CHECK_TASK_ATTACHMENT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkTaskAttachment(
            @RequestParam(value = "fileId") String fileId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = ADD_TASK_ATTACHMENT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void addTaskAttachment(
            @RequestParam(value = "fileId") String fileId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = DELETE_TASK_ATTACHMENT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void deleteTaskAttachment(
            @RequestParam(value = "fileId") String fileId,
            @RequestParam(value = "taskId") String taskId
    );

    @RequestMapping(value = GET_TASK_ATTACHMENTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    ArrayList<RelationDTO> getTaskAttachments(
            @RequestParam(value = "taskId") String taskId
    );
}
