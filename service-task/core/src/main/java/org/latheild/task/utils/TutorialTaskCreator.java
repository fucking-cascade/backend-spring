package org.latheild.task.utils;

import org.latheild.common.api.CommonPriority;
import org.latheild.common.api.CommonTaskStatus;
import org.latheild.task.api.dto.TaskDTO;

import java.util.ArrayList;

public class TutorialTaskCreator {
    public static ArrayList<TaskDTO> setTutorialTasks(String ownerId, String progressId) {
        ArrayList<TaskDTO> taskDTOs = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setIndex(i);
            taskDTO.setOwnerId(ownerId);
            taskDTO.setProgressId(progressId);
            taskDTO.setTaskStatus(CommonTaskStatus.ONGOING);
            taskDTO.setPriority(CommonPriority.HIGH_PRIORITY);
            switch (i) {
                case 0:
                    taskDTO.setName("Tutorial task v0.00001");
                    taskDTO.setContent("This is a tutorial task created by Emoin LANYU");
                    break;
                case 1:
                    taskDTO.setName("Tutorial task v0.00002");
                    taskDTO.setContent("This is a tutorial task created by Emoin LANYU");
                    break;
                case 2:
                    taskDTO.setName("Tutorial task v0.00003");
                    taskDTO.setContent("This is a tutorial task created by Emoin LANYU");
                    break;
            }
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }
}
