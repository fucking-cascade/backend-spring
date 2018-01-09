package org.latheild.progress.dao;

import org.latheild.common.domain.DAOResponse;
import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.progress.domain.Progress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.constant.BaseURI.*;

@SuppressWarnings("unchecked")
public class ProgressRepositoryImpl extends DAOResponseJSONAnalyzer implements ProgressRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/progress";

    private Wrapper<Progress> analyzer = new Wrapper<>(new Progress());

    @Override
    public Progress findById(String id) {
        return (Progress) analyzer.analyze(
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
    public ArrayList<Progress> findAllByOwnerId(String ownerId) {
        return (ArrayList<Progress>) analyzer.analyze(
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
    public ArrayList<Progress> findAllByProjectId(String projectId) {
        return (ArrayList<Progress>) analyzer.analyze(
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
    public ArrayList<Progress> findAllByProjectIdOrderByIndexAsc(String projectId) {
        return (ArrayList<Progress>) analyzer.analyze(
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
    public ArrayList<Progress> findAllByOwnerIdAndProjectId(String ownerId, String projectId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("OwnerId");
        fieldNames.add("ProjectId");
        Progress progress = new Progress();
        progress.setProjectId(projectId);
        progress.setOwnerId(ownerId);
        return (ArrayList<Progress>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON(fieldNames, progress, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<Progress> findAll() {
        return (ArrayList<Progress>) analyzer.analyze(
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
    public void deleteAllByOwnerIdAndProjectId(String ownerId, String projectId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("OwnerId");
        fieldNames.add("ProjectId");
        Progress progress = new Progress();
        progress.setOwnerId(ownerId);
        progress.setProjectId(projectId);
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON(fieldNames, progress),
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
    public void save(Progress progress) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("OwnerId");
        fieldNames.add("name");
        fieldNames.add("ProjectId");
        fieldNames.add("order");
        if (progress.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, progress),
                    DAOResponse.class
            );
        } else {
            Progress response = (Progress) analyzer.analyze(
                    restTemplate.postForObject(
                            CombineURI.combineURI(
                                    SERVICE_URI, BASE_CREATE
                            ),
                            DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, progress),
                            DAOResponse.class
                    )
            );
            progress.setId(response.getId());
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
