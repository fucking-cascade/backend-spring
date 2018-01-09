package org.latheild.task.dao;

import org.latheild.common.domain.DAOResponse;
import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.task.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.constant.BaseURI.*;

@SuppressWarnings("unchecked")
public class TaskRepositoryImpl extends DAOResponseJSONAnalyzer implements TaskRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/task";

    private Wrapper<Task> analyzer = new Wrapper<>(new Task());

    @Override
    public Task findById(String id) {
        return (Task) analyzer.analyze(
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
    public ArrayList<Task> findAllByOwnerId(String ownerId) {
        return (ArrayList<Task>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON("OwnerId", ownerId, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<Task> findAllByProgressId(String progressId) {
        return (ArrayList<Task>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON("ProgressId", progressId, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<Task> findAllByOwnerIdAndProgressId(String ownerId, String progressId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("OwnerId");
        fieldNames.add("ProgressId");
        Task task = new Task();
        task.setProgressId(progressId);
        task.setOwnerId(ownerId);
        return (ArrayList<Task>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON(fieldNames, task, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<Task> findAll() {
        return (ArrayList<Task>) analyzer.analyze(
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
    public int countByOwnerId(String ownerId) {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON("OwnerId", ownerId),
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }

    @Override
    public int countByProgressId(String progressId) {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON("ProgressId", progressId),
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
    public void deleteAllByOwnerId(String ownerId) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("OwnerId", ownerId),
                DAOResponse.class
        );
    }

    @Override
    public void deleteAllByProgressId(String progressId) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("ProgressId", progressId),
                DAOResponse.class
        );
    }

    @Override
    public void deleteAllByOwnerIdAndProgressId(String ownerId, String progressId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("OwnerId");
        fieldNames.add("ProgressId");
        Task task = new Task();
        task.setProgressId(progressId);
        task.setOwnerId(ownerId);
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON(fieldNames, task),
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
    public void save(Task task) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("OwnerId");
        fieldNames.add("ProgressId");
        fieldNames.add("name");
        fieldNames.add("content");
        fieldNames.add("state");
        fieldNames.add("ddl");
        if (task.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, task),
                    DAOResponse.class
            );
        } else {
            Task response = (Task) analyzer.analyze(
                    restTemplate.postForObject(
                            CombineURI.combineURI(
                                    SERVICE_URI, BASE_CREATE
                            ),
                            DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, task),
                            DAOResponse.class
                    )
            );
            task.setId(response.getId());
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
