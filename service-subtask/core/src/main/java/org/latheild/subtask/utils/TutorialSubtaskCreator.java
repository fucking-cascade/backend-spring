package org.latheild.subtask.utils;

import org.latheild.common.api.CommonTaskStatus;
import org.latheild.subtask.api.dto.SubtaskDTO;

import java.util.ArrayList;

public class TutorialSubtaskCreator {
    public static ArrayList<SubtaskDTO> setTutorialSubtasks(String userId, String taskId) {
        ArrayList<SubtaskDTO> subtaskDTOs = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            SubtaskDTO subtaskDTO = new SubtaskDTO();
            subtaskDTO.setUserId(userId);
            subtaskDTO.setTaskId(taskId);
            subtaskDTO.setState(false);
            switch (i) {
                case 0:
                    subtaskDTO.setContent("Tutorial subtask v0.00000001 by Emoin LANYU");
                    break;
                case 1:
                    subtaskDTO.setContent("Tutorial subtask v0.00000002 by Emoin LANYU");
                    break;
                case 2:
                    subtaskDTO.setContent("Tutorial subtask v0.00000003 by Emoin LANYU");
                    break;
            }
            subtaskDTOs.add(subtaskDTO);
        }
        return subtaskDTOs;
    }
}
