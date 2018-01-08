package org.latheild.relation.core.dao;

import org.latheild.common.domain.DAOResponse;
import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.relation.core.domain.UserProjectRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.constant.BaseURI.*;
import static org.latheild.common.constant.BaseURI.BASE_CREATE;

@SuppressWarnings("unchecked")
public class UserProjectRelationRepositoryImpl extends DAOResponseJSONAnalyzer implements UserProjectRelationRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/projectuser";

    private Wrapper<UserProjectRelation> analyzer = new Wrapper<>(new UserProjectRelation());

    @Override
    public UserProjectRelation findById(String id) {
        return (UserProjectRelation) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON("id", id, DAORequestJSONWrapper.FIND_ONE),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<UserProjectRelation> findAllByUserId(String userId) {
        return (ArrayList<UserProjectRelation>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON("UserId", userId, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<UserProjectRelation> findAllByProjectId(String projectId) {
        return (ArrayList<UserProjectRelation>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON("ProjectId", projectId, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public UserProjectRelation findByUserIdAndProjectId(String userId, String projectId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("userId");
        fieldNames.add("projectId");
        UserProjectRelation userProjectRelation = new UserProjectRelation();
        userProjectRelation.setProjectId(projectId);
        userProjectRelation.setUserId(userId);
        return (UserProjectRelation) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON(fieldNames, userProjectRelation, DAORequestJSONWrapper.FIND_ONE),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<UserProjectRelation> findAll() {
        return (ArrayList<UserProjectRelation>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        "",
                        DAOResponse.class
                )
        );
    }

    @Override
    public int countById(String id) {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON("id", id),
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }

    @Override
    public int countByUserId(String userId) {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON("UserId", userId),
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }

    @Override
    public int countByProjectId(String projectId) {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON("ProjectId", projectId),
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }

    @Override
    public int countByUserIdAndProjectId(String userId, String projectId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("fileId");
        fieldNames.add("taskId");
        UserProjectRelation userProjectRelation = new UserProjectRelation();
        userProjectRelation.setUserId(userId);
        userProjectRelation.setProjectId(projectId);
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON(fieldNames, userProjectRelation),
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }

    @Override
    public void deleteById(String id) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("id", id),
                DAOResponse.class
        );
    }

    @Override
    public void deleteAllByUserId(String userId) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("UserId", userId),
                DAOResponse.class
        );
    }

    @Override
    public void deleteAllByProjectId(String projectId) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("ProjectId", projectId),
                DAOResponse.class
        );
    }

    @Override
    public void deleteByUserIdAndProjectId(String userId, String projectId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("fileId");
        fieldNames.add("taskId");
        UserProjectRelation userProjectRelation = new UserProjectRelation();
        userProjectRelation.setUserId(userId);
        userProjectRelation.setProjectId(projectId);
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON(fieldNames, userProjectRelation),
                DAOResponse.class
        );
    }

    @Override
    public void deleteAll() {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                "",
                DAOResponse.class
        );
    }

    @Override
    public void save(UserProjectRelation userProjectRelation) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("userId");
        fieldNames.add("taskId");
        if (userProjectRelation.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, userProjectRelation),
                    DAOResponse.class
            );
        } else {
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_CREATE
                    ),
                    DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, userProjectRelation),
                    DAOResponse.class
            );
        }
    }

    @Override
    public int count() {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                "",
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }
}
