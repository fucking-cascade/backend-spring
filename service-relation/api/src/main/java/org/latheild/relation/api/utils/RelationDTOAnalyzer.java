package org.latheild.relation.api.utils;

import org.latheild.common.api.CommonIdentityType;
import org.latheild.common.constant.RelationType;
import org.latheild.relation.api.dto.RelationDTO;

public class RelationDTOAnalyzer {
    public static String getUserId(RelationDTO relationDTO) throws Exception {
        if (relationDTO.getRelationType() == RelationType.FILE_AND_TASK
                || relationDTO.getRelationType() == RelationType.TASK_AND_FILE) {
            throw new Exception("Relation type error: not related to user");
        } else if (relationDTO.getRelationType() == RelationType.USER_AND_PROJECT
                || relationDTO.getRelationType() == RelationType.USER_AND_SCHEDULE
                || relationDTO.getRelationType() == RelationType.USER_AND_TASK) {
            return relationDTO.getStakeHolder();
        } else {
            return relationDTO.getProperty();
        }
    }

    public static String getProjectId(RelationDTO relationDTO) throws Exception {
        if (relationDTO.getRelationType() != RelationType.PROJECT_AND_USER
                && relationDTO.getRelationType() != RelationType.USER_AND_PROJECT) {
            throw new Exception("Relation type error: not related to project");
        } else if (relationDTO.getRelationType() == RelationType.PROJECT_AND_USER) {
            return relationDTO.getStakeHolder();
        } else {
            return relationDTO.getProperty();
        }
    }

    public static String getScheduleId(RelationDTO relationDTO) throws Exception {
        if (relationDTO.getRelationType() != RelationType.SCHEDULE_AND_USER
                && relationDTO.getRelationType() != RelationType.USER_AND_SCHEDULE) {
            throw new Exception("Relation type error: not related to schedule");
        } else if (relationDTO.getRelationType() == RelationType.SCHEDULE_AND_USER) {
            return relationDTO.getStakeHolder();
        } else {
            return relationDTO.getProperty();
        }
    }

    public static String getTaskId(RelationDTO relationDTO) throws Exception {
        if (relationDTO.getRelationType() != RelationType.TASK_AND_FILE
                && relationDTO.getRelationType() != RelationType.TASK_AND_USER
                && relationDTO.getRelationType() != RelationType.USER_AND_TASK
                && relationDTO.getRelationType() != RelationType.FILE_AND_TASK) {
            throw new Exception("Relation type error: not related to task");
        } else if (relationDTO.getRelationType() == RelationType.TASK_AND_USER
                || relationDTO.getRelationType() == RelationType.TASK_AND_FILE) {
            return relationDTO.getStakeHolder();
        } else {
            return relationDTO.getProperty();
        }
    }

    public static String getFileId(RelationDTO relationDTO) throws Exception {
        if (relationDTO.getRelationType() != RelationType.FILE_AND_TASK
                && relationDTO.getRelationType() != RelationType.TASK_AND_FILE) {
            throw new Exception("Relation type error: not related to file");
        } else if (relationDTO.getRelationType() == RelationType.FILE_AND_TASK) {
            return relationDTO.getStakeHolder();
        } else {
            return relationDTO.getProperty();
        }
    }

    public static CommonIdentityType getIdentityType(RelationDTO relationDTO) throws Exception {
        if (relationDTO.getRelationType() != RelationType.PROJECT_AND_USER
                && relationDTO.getRelationType() != RelationType.USER_AND_PROJECT) {
            throw new Exception("Relation type error: not related to project");
        } else {
            return relationDTO.getIdentityType();
        }
    }
}
