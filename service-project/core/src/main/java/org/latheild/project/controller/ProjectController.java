package org.latheild.project.controller;

import org.latheild.apiutils.api.BaseResponseBody;
import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ExceptionResponseBody;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.project.api.dto.ProjectMemberOperationDTO;
import org.latheild.project.api.dto.ChangeOwnerDTO;
import org.latheild.project.api.dto.ProjectDTO;
import org.latheild.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.project.api.constant.ProjectURL.*;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @RequestMapping(value = CHECK_PROJECT_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkProjectExistence(
            @RequestParam(value = "projectId") String projectId
    ) {
        return projectService.checkProjectExistence(projectId);
    }

    @RequestMapping(value = CREATE_PROJECT_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object createProject(
            @RequestBody ProjectDTO projectDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, projectService.createProject(projectDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = UPDATE_PROJECT_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object updateProjectInfo(
            @RequestBody ProjectDTO projectDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, projectService.updateProjectInfo(projectDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = CHANGE_PROJECT_OWNER_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object changeProjectOwner(
            @RequestBody ChangeOwnerDTO changeOwnerDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, projectService.changeProjectOwner(changeOwnerDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = DELETE_PROJECT_BY_ID_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object deleteProjectById(
            @RequestBody ProjectDTO projectDTO
    ) {
        try {
            projectService.deleteProjectById(projectDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_PROJECT_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getProjectById(
            @RequestParam(value = "id") String id
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, projectService.getProjectById(id));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_PROJECTS_BY_USER_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getProjectsByUserId(
            @RequestParam(value = "ownerId", required = false) String ownerId,
            @RequestParam(value = "userId", required = false) String userId
    ) {
        try {
            if (ownerId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, projectService.getProjectsByOwnerId(ownerId));
            } else if (userId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, projectService.getAllProjectsByUserId(userId));
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INTERNAL_ERROR
                );
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_GET_ALL_PROJECTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminGetAllProjects(
            @RequestParam(value = "code") String code
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, projectService.adminGetAllProjects(code));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_PROJECT_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteProjectById(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "code") String code
    ) {
        try {
            projectService.adminDeleteProjectById(id, code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_PROJECTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteProjects(
            @RequestParam(value = "ownerId", required = false) String ownerId,
            @RequestParam(value = "code") String code
    ) {
        try {
            if (ownerId != null) {
                projectService.adminDeleteProjectsByOwnerId(ownerId, code);
            } else {
                projectService.adminDeleteAllProjects(code);
            }
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADD_PROJECT_MEMBER_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object addProjectMember(
            @RequestBody ProjectMemberOperationDTO projectMemberOperationDTO
    ) {
        try {
            projectService.addProjectMember(projectMemberOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = REMOVE_PROJECT_MEMBER_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object removeProjectMember(
            @RequestBody ProjectMemberOperationDTO projectMemberOperationDTO
    ) {
        try {
            projectService.removeProjectMember(projectMemberOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }
}
