package org.latheild.relation.core.dao;

import org.latheild.common.domain.DAOResponse;
import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.relation.core.domain.UserTaskRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.constant.BaseURI.*;

@SuppressWarnings("unchecked")
public class UserTaskRelationRepositoryImpl extends DAOResponseJSONAnalyzer implements UserTaskRelationRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/taskuser";

    private Wrapper<UserTaskRelation> analyzer = new Wrapper<>(new UserTaskRelation());

    @Override
    public UserTaskRelation findById(String id) {
        return (UserTaskRelation) analyzer.analyze(
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
    public ArrayList<UserTaskRelation> findAllByUserId(String userId) {
        return (ArrayList<UserTaskRelation>) analyzer.analyze(
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
    public ArrayList<UserTaskRelation> findAllByTaskId(String taskId) {
        return (ArrayList<UserTaskRelation>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON("TaskId", taskId, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public UserTaskRelation findByUserIdAndTaskId(String userId, String taskId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("TaskId");
        UserTaskRelation userTaskRelation = new UserTaskRelation();
        userTaskRelation.setTaskId(taskId);
        userTaskRelation.setUserId(userId);
        return (UserTaskRelation) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON(fieldNames, userTaskRelation, DAORequestJSONWrapper.FIND_ONE),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<UserTaskRelation> findAll() {
        return (ArrayList<UserTaskRelation>) analyzer.analyze(
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
    public int countByTaskId(String taskId) {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON("TaskId", taskId),
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }

    @Override
    public int countByUserIdAndTaskId(String userId, String taskId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("TaskId");
        UserTaskRelation userTaskRelation = new UserTaskRelation();
        userTaskRelation.setUserId(userId);
        userTaskRelation.setTaskId(taskId);
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON(fieldNames, userTaskRelation),
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
    public void deleteAllByTaskId(String taskId) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("TaskId", taskId),
                DAOResponse.class
        );
    }

    @Override
    public void deleteByUserIdAndTaskId(String userId, String taskId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("TaskId");
        UserTaskRelation userTaskRelation = new UserTaskRelation();
        userTaskRelation.setUserId(userId);
        userTaskRelation.setTaskId(taskId);
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON(fieldNames, userTaskRelation),
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
    public void save(UserTaskRelation userTaskRelation) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("TaskId");
        if (userTaskRelation.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, userTaskRelation),
                    DAOResponse.class
            );
        } else {
            UserTaskRelation response = (UserTaskRelation) analyzer.analyze(
                    restTemplate.postForObject(
                            CombineURI.combineURI(
                                    SERVICE_URI, BASE_CREATE
                            ),
                            DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, userTaskRelation),
                            DAOResponse.class
                    )
            );
            userTaskRelation.setId(response.getId());
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
