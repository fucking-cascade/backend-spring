package org.latheild.subtask.dao;

import org.latheild.common.domain.DAOResponse;
import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.subtask.domain.Subtask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.constant.BaseURI.*;

@SuppressWarnings("unchecked")
public class SubtaskRepositoryImpl extends DAOResponseJSONAnalyzer implements SubtaskRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/subtask";

    private Wrapper<Subtask> analyzer = new Wrapper<>(new Subtask());

    @Override
    public Subtask findById(String id) {
        return (Subtask) analyzer.analyze(
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
    public ArrayList<Subtask> findAllByUserId(String userId) {
        return (ArrayList<Subtask>) analyzer.analyze(
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
    public ArrayList<Subtask> findAllByTaskId(String taskId) {
        return (ArrayList<Subtask>) analyzer.analyze(
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
    public ArrayList<Subtask> findAllByUserIdAndTaskId(String userId, String taskId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("TaskId");
        Subtask subtask = new Subtask();
        subtask.setTaskId(taskId);
        subtask.setUserId(userId);
        return (ArrayList<Subtask>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON(fieldNames, subtask, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<Subtask> findAll() {
        return (ArrayList<Subtask>) analyzer.analyze(
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
    public void deleteAllByUserIdAndTaskId(String userId, String taskId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("TaskId");
        Subtask subtask = new Subtask();
        subtask.setTaskId(taskId);
        subtask.setUserId(userId);
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON(fieldNames, subtask),
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
    public void save(Subtask subtask) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("TaskId");
        fieldNames.add("content");
        fieldNames.add("state");
        if (subtask.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, subtask),
                    DAOResponse.class
            );
        } else {
            Subtask response = (Subtask) analyzer.analyze(
                    restTemplate.postForObject(
                            CombineURI.combineURI(
                                    SERVICE_URI, BASE_CREATE
                            ),
                            DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, subtask),
                            DAOResponse.class
                    )
            );
            subtask.setId(response.getId());
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
