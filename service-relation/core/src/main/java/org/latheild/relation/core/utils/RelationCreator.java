package org.latheild.relation.core.utils;

import org.latheild.common.api.CommonIdentityType;
import org.latheild.relation.core.domain.FileTaskRelation;
import org.latheild.relation.core.domain.UserProjectRelation;
import org.latheild.relation.core.domain.UserScheduleRelation;
import org.latheild.relation.core.domain.UserTaskRelation;

public class RelationCreator {
    public static UserProjectRelation setUserProjectRelation(String userId, String projectId, CommonIdentityType identityType) {
        UserProjectRelation userProjectRelation = new UserProjectRelation();
        userProjectRelation.setUserId(userId);
        userProjectRelation.setProjectId(projectId);
        userProjectRelation.setIdentityType(identityType);
        return userProjectRelation;
    }

    public static UserTaskRelation setUserTaskRelation(String userId, String taskId) {
        UserTaskRelation userTaskRelation = new UserTaskRelation();
        userTaskRelation.setUserId(userId);
        userTaskRelation.setTaskId(taskId);
        return userTaskRelation;
    }

    public static UserScheduleRelation setUserScheduleRelation(String userId, String scheduleId) {
        UserScheduleRelation userScheduleRelation = new UserScheduleRelation();
        userScheduleRelation.setUserId(userId);
        userScheduleRelation.setScheduleId(scheduleId);
        return userScheduleRelation;
    }

    public static FileTaskRelation setFileTaskRelation(String fileId, String taskId) {
        FileTaskRelation fileTaskRelation = new FileTaskRelation();
        fileTaskRelation.setFileId(fileId);
        fileTaskRelation.setTaskId(taskId);
        return fileTaskRelation;
    }
}
