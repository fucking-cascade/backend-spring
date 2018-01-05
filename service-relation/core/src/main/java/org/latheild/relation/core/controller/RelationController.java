package org.latheild.relation.core.controller;

import org.latheild.common.api.CommonIdentityType;
import org.latheild.relation.api.dto.RelationDTO;
import org.latheild.relation.core.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.relation.api.constant.RelationURL.*;

@RestController
public class RelationController {
    @Autowired
    RelationService relationService;

    @RequestMapping(value = CHECK_PROJECT_MEMBER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkProjectMember(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    ) {
        return relationService.checkProjectMemberExistence(userId, projectId);
    }

    @RequestMapping(value = CHECK_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkScheduleParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "scheduleId") String scheduleId
    ) {
        return relationService.checkScheduleParticipantExistence(userId, scheduleId);
    }

    @RequestMapping(value = CHECK_TASK_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkTaskParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "taskId") String taskId
    ) {
        return relationService.checkTaskParticipantExistence(userId, taskId);
    }

    @RequestMapping(value = CHECK_TASK_ATTACHMENT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkTaskAttachment(
            @RequestParam(value = "fileId") String fileId,
            @RequestParam(value = "taskId") String taskId
    ) {
        return relationService.checkTaskAttachmentExistence(fileId, taskId);
    }

    @RequestMapping(value = ADD_PROJECT_MEMBER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public void addProjectMember(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId,
            @RequestParam(value = "identityType") CommonIdentityType identityType
    ) {
        relationService.addProjectMember(userId, projectId, identityType);
    }

    @RequestMapping(value = ADD_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public void addScheduleParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "scheduleId") String scheduleId
    ) {
        relationService.addScheduleParticipant(userId, scheduleId);
    }

    @RequestMapping(value = ADD_TASK_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public void addTaskParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "taskId") String taskId
    ) {
        relationService.addTaskParticipant(userId, taskId);
    }

    @RequestMapping(value = ADD_TASK_ATTACHMENT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public void addTaskAttachment(
            @RequestParam(value = "fileId") String fileId,
            @RequestParam(value = "taskId") String taskId
    ) {
        relationService.addTaskAttachment(fileId, taskId);
    }

    @RequestMapping(value = DELETE_PROJECT_MEMBER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public void deleteProjectMember(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    ) {
        relationService.deleteProjectMember(userId, projectId);
    }

    @RequestMapping(value = DELETE_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public void deleteScheduleParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "scheduleId") String scheduleId
    ) {
        relationService.deleteScheduleParticipant(userId, scheduleId);
    }

    @RequestMapping(value = DELETE_TASK_PARTICIPANT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public void deleteTaskParticipant(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "taskId") String taskId
    ) {
        relationService.deleteTaskParticipant(userId, taskId);
    }

    @RequestMapping(value = DELETE_TASK_ATTACHMENT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public void deleteTaskAttachment(
            @RequestParam(value = "fileId") String fileId,
            @RequestParam(value = "taskId") String taskId
    ) {
        relationService.deleteTaskAttachment(fileId, taskId);
    }

    @RequestMapping(value = GET_PROJECT_MEMBERS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public ArrayList<RelationDTO> getProjectMembers(
            @RequestParam(value = "projectId") String projectId
    ) {
        return relationService.getProjectMembers(projectId);
    }

    @RequestMapping(value = GET_SCHEDULE_PARTICIPANTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public ArrayList<RelationDTO> getScheduleParticipants(
            @RequestParam(value = "scheduleId") String scheduleId
    ) {
        return relationService.getScheduleParticipants(scheduleId);
    }

    @RequestMapping(value = GET_TASK_PARTICIPANTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public ArrayList<RelationDTO> getTaskParticipants(
            @RequestParam(value = "taskId") String taskId
    ) {
        return relationService.getTaskParticipants(taskId);
    }

    @RequestMapping(value = GET_TASK_ATTACHMENTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public ArrayList<RelationDTO> getTaskAttachments(
            @RequestParam(value = "taskId") String taskId
    ) {
        return relationService.getTaskAttachments(taskId);
    }

    @RequestMapping(value = GET_USER_PROJECTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public ArrayList<RelationDTO> getUserProjects(
            @RequestParam(value = "userId") String userId
    ) {
        return relationService.getUserProjects(userId);
    }

    @RequestMapping(value = GET_USER_SCHEDULES_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public ArrayList<RelationDTO> getUserSchedules(
            @RequestParam(value = "userId") String userId
    ) {
        return relationService.getUserSchedules(userId);
    }

    @RequestMapping(value = GET_USER_TASKS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public ArrayList<RelationDTO> getUserTasks(
            @RequestParam(value = "userId") String userId
    ) {
        return relationService.getUserTasks(userId);
    }

    @RequestMapping(value = GET_FILE_TASKS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public ArrayList<RelationDTO> getFileTasks(
            @RequestParam(value = "fileId") String fileId
    ) {
        return relationService.getFileTasks(fileId);
    }

    @RequestMapping(value = GET_MEMBER_IDENTITY_OF_PROJECT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public CommonIdentityType getMemberIdentityOfProject(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    ) {
        return relationService.getMemberIdentityOfProject(userId, projectId);
    }
}
