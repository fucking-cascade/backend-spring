package org.latheild.relation.core.utils;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.common.constant.RelationType;
import org.latheild.relation.api.dto.RelationDTO;
import org.latheild.relation.core.domain.FileTaskRelation;
import org.latheild.relation.core.domain.UserProjectRelation;
import org.latheild.relation.core.domain.UserScheduleRelation;
import org.latheild.relation.core.domain.UserTaskRelation;

import java.util.ArrayList;

public class RelationToDTOConverter {
    private static RelationDTO setRelationDTO(String stakeHolder, String property, RelationType relationType) {
        RelationDTO relationDTO = new RelationDTO();
        relationDTO.setStakeHolder(stakeHolder);
        relationDTO.setProperty(property);
        relationDTO.setRelationType(relationType);
        return relationDTO;
    }

    public static ArrayList<RelationDTO> convertUserProjectList(ArrayList<UserProjectRelation> userProjectRelations, RelationType relationType) {
        ArrayList<RelationDTO> relationDTOs = new ArrayList<>();
        RelationDTO relationDTO;
        for (UserProjectRelation userProjectRelation : userProjectRelations) {
            switch (relationType) {
                case USER_AND_PROJECT:
                    relationDTO = setRelationDTO(
                            userProjectRelation.getUserId(),
                            userProjectRelation.getProjectId(),
                            relationType
                    );
                    break;
                case PROJECT_AND_USER:
                    relationDTO = setRelationDTO(
                            userProjectRelation.getProjectId(),
                            userProjectRelation.getUserId(),
                            relationType
                    );
                    break;
                default:
                    throw new AppBusinessException(
                            CommonErrorCode.INTERNAL_ERROR
                    );
            }
            relationDTO.setIdentityType(userProjectRelation.getIdentityType());
            relationDTOs.add(relationDTO);
        }
        return relationDTOs;
    }

    public static ArrayList<RelationDTO> convertUserScheduleList(ArrayList<UserScheduleRelation> userScheduleRelations, RelationType relationType) {
        ArrayList<RelationDTO> relationDTOs = new ArrayList<>();
        RelationDTO relationDTO;
        for (UserScheduleRelation userScheduleRelation : userScheduleRelations) {
            switch (relationType) {
                case USER_AND_SCHEDULE:
                    relationDTO = setRelationDTO(
                            userScheduleRelation.getUserId(),
                            userScheduleRelation.getScheduleId(),
                            relationType
                    );
                    break;
                case SCHEDULE_AND_USER:
                    relationDTO = setRelationDTO(
                            userScheduleRelation.getScheduleId(),
                            userScheduleRelation.getUserId(),
                            relationType
                    );
                    break;
                default:
                    throw new AppBusinessException(
                            CommonErrorCode.INTERNAL_ERROR
                    );
            }
            relationDTOs.add(relationDTO);
        }
        return relationDTOs;
    }

    public static ArrayList<RelationDTO> convertUserTaskList(ArrayList<UserTaskRelation> userTaskRelations, RelationType relationType) {
        ArrayList<RelationDTO> relationDTOs = new ArrayList<>();
        RelationDTO relationDTO;
        for (UserTaskRelation userTaskRelation : userTaskRelations) {
            switch (relationType) {
                case USER_AND_TASK:
                    relationDTO = setRelationDTO(
                            userTaskRelation.getUserId(),
                            userTaskRelation.getTaskId(),
                            relationType
                    );
                    break;
                case TASK_AND_USER:
                    relationDTO = setRelationDTO(
                            userTaskRelation.getTaskId(),
                            userTaskRelation.getUserId(),
                            relationType
                    );
                    break;
                default:
                    throw new AppBusinessException(
                            CommonErrorCode.INTERNAL_ERROR
                    );
            }
            relationDTOs.add(relationDTO);
        }
        return relationDTOs;
    }

    public static ArrayList<RelationDTO> convertFileTaskList(ArrayList<FileTaskRelation> fileTaskRelations, RelationType relationType) {
        ArrayList<RelationDTO> relationDTOs = new ArrayList<>();
        RelationDTO relationDTO;
        for (FileTaskRelation fileTaskRelation : fileTaskRelations) {
            switch (relationType) {
                case FILE_AND_TASK:
                    relationDTO = setRelationDTO(
                            fileTaskRelation.getFileId(),
                            fileTaskRelation.getTaskId(),
                            relationType
                    );
                    break;
                case TASK_AND_FILE:
                    relationDTO = setRelationDTO(
                            fileTaskRelation.getTaskId(),
                            fileTaskRelation.getFileId(),
                            relationType
                    );
                    break;
                default:
                    throw new AppBusinessException(
                            CommonErrorCode.INTERNAL_ERROR
                    );
            }
            relationDTOs.add(relationDTO);
        }
        return relationDTOs;
    }
}
