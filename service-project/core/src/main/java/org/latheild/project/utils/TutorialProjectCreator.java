package org.latheild.project.utils;

import org.latheild.project.api.dto.ProjectDTO;

public class TutorialProjectCreator {
    public static ProjectDTO setTutorialProject(String ownerId) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setOwnerId(ownerId);
        projectDTO.setName("Tutorial p01");
        projectDTO.setDescription("This is a tutorial");
        return projectDTO;
    }
}
