package org.latheild.project.service;

import org.latheild.project.api.dto.ChangeOwnerDTO;
import org.latheild.project.api.dto.ProjectDTO;

import java.util.ArrayList;

public interface ProjectService {
    public ProjectDTO createProject(ProjectDTO projectDTO);

    public ProjectDTO updateProjectInfo(ProjectDTO projectDTO);

    public ProjectDTO changeProjectOwner(ChangeOwnerDTO changeOwnerDTO);

    public void deleteProjectById(ProjectDTO projectDTO);

    public ProjectDTO getProjectById(String id);

    public ArrayList<ProjectDTO> getProjectsByOwnerId(String ownerId);

    public ArrayList<ProjectDTO> adminGetAllProjects(String code);

    public void adminDeleteProjectById(String id, String code);

    public void adminDeleteProjectsByOwnerId(String ownerId, String code);

    public void adminDeleteAllProjects(String code);
}
