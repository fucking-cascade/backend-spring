package org.latheild.relation.core.service;

import org.latheild.common.api.CommonIdentityType;
import org.latheild.relation.api.dto.RelationDTO;

import java.util.ArrayList;

public interface RelationService {
    boolean checkProjectMemberExistence(String userId, String projectId);

    boolean checkScheduleParticipantExistence(String userId, String scheduleId);

    boolean checkTaskParticipantExistence(String userId, String taskId);

    boolean checkTaskAttachmentExistence(String fileId, String taskId);

    void addProjectMember(String userId, String projectId, CommonIdentityType identityType);

    void addScheduleParticipant(String userId, String scheduleId);

    void addTaskParticipant(String userId, String taskId);

    void addTaskAttachment(String fileId, String taskId);

    void deleteProjectMember(String userId, String projectId);

    void deleteScheduleParticipant(String userId, String scheduleId);

    void deleteTaskParticipant(String userId, String taskId);

    void deleteTaskAttachment(String fileId, String taskId);

    ArrayList<RelationDTO> getProjectMembers(String projectId);

    ArrayList<RelationDTO> getScheduleParticipants(String scheduleId);

    ArrayList<RelationDTO> getTaskParticipants(String taskId);

    ArrayList<RelationDTO> getTaskAttachments(String taskId);

    ArrayList<RelationDTO> getUserProjects(String userId);

    ArrayList<RelationDTO> getUserSchedules(String userId);

    ArrayList<RelationDTO> getUserTasks(String userId);

    ArrayList<RelationDTO> getFileTasks(String fileId);

    CommonIdentityType getMemberIdentityOfProject(String userId, String projectId);
}
