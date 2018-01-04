package org.latheild.project.utils;

import org.latheild.project.api.dto.ProjectDTO;

public class TutorialProjectCreator {
    public static ProjectDTO setTutorialProject(String ownerId) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setOwnerId(ownerId);
        projectDTO.setName("Tutorial project v0.000000001");
        projectDTO.setDescription("This is a tutorial project provided by Emoin LANYU of team cascade");
        return projectDTO;
    }
}
