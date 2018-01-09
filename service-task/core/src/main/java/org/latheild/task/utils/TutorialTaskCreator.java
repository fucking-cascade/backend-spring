package org.latheild.task.utils;

import org.latheild.task.api.dto.TaskDTO;

import java.util.ArrayList;

public class TutorialTaskCreator {
    public static ArrayList<TaskDTO> setTutorialTasks(String ownerId, String progressId) {
        ArrayList<TaskDTO> taskDTOs = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.setOwnerId(ownerId);
            taskDTO.setProgressId(progressId);
            taskDTO.setState(false);
            taskDTO.setDdl("1996-08-08");
            switch (i) {
                case 0:
                    taskDTO.setName("Tutori0001");
                    taskDTO.setContent("This is a tutorial task");
                    break;
                case 1:
                    taskDTO.setName("Tu0002");
                    taskDTO.setContent("This is a tutorial task");
                    break;
                case 2:
                    taskDTO.setName("Tutor0003");
                    taskDTO.setContent("This is a tutorial task");
                    break;
            }
            taskDTOs.add(taskDTO);
        }
        return taskDTOs;
    }
}
