package org.latheild.project.client;

import org.latheild.common.api.CommonIdentityType;
import org.latheild.relation.api.dto.RelationDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.relation.api.constant.RelationURL.*;

@FeignClient(name = "relation-service")
public interface RelationClient {
    @RequestMapping(value = CHECK_PROJECT_MEMBER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    boolean checkProjectMember(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    );

    @RequestMapping(value = ADD_PROJECT_MEMBER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void addProjectMember(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    );

    @RequestMapping(value = DELETE_PROJECT_MEMBER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    void deleteProjectMember(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    );

    @RequestMapping(value = GET_USER_PROJECTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    ArrayList<RelationDTO> getUserProjects(
            @RequestParam(value = "userId") String userId
    );

    @RequestMapping(value = GET_MEMBER_IDENTITY_OF_PROJECT_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    CommonIdentityType getMemberIdentityOfProject(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "projectId") String projectId
    );
}
