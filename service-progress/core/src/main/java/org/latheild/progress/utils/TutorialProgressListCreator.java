package org.latheild.progress.utils;

import org.latheild.progress.api.dto.ProgressDTO;

import java.util.ArrayList;

public class TutorialProgressListCreator {
    public static ArrayList<ProgressDTO> setTutorialProgressList(String ownerId, String projectId) {
        ArrayList<ProgressDTO> progressDTOs = new ArrayList<>();
        ProgressDTO temp;
        for (int i = 0; i < 3; ++i) {
            temp = new ProgressDTO();
            temp.setIndex(i);
            temp.setProjectId(projectId);
            temp.setOwnerId(ownerId);
            switch (i) {
                case 0:
                    temp.setName("Tutorial progress prepare");
                    break;
                case 1:
                    temp.setName("Tutorial progress ongoing");
                    break;
                case 2:
                    temp.setName("Tutorial progress done");
                    break;
            }
            progressDTOs.add(temp);
        }
        return progressDTOs;
    }
}
