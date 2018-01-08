package org.latheild.file.dao;

import org.latheild.common.domain.DAOResponse;
import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.file.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.constant.BaseURI.*;

@SuppressWarnings("unchecked")
public class FileRepositoryImpl extends DAOResponseJSONAnalyzer implements FileRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/file";

    private Wrapper<File> analyzer = new Wrapper<>(new File());

    @Override
    public File findById(String id) {
        return (File) analyzer.analyze(
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
    public ArrayList<File> findAllByOwnerId(String ownerId) {
        return (ArrayList<File>) analyzer.analyze(
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
    public ArrayList<File> findAllByProjectId(String projectId) {
        return (ArrayList<File>) analyzer.analyze(
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
    public ArrayList<File> findAllByOwnerIdAndProjectId(String ownerId, String projectId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("ownerId");
        fieldNames.add("projectId");
        File file = new File();
        file.setOwnerId(ownerId);
        file.setProjectId(projectId);
        return (ArrayList<File>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON(fieldNames, file, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<File> findAll() {
        return (ArrayList<File>) analyzer.analyze(
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
        fieldNames.add("ownerId");
        fieldNames.add("projectId");
        File file = new File();
        file.setOwnerId(ownerId);
        file.setProjectId(projectId);
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON(fieldNames, file),
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
    public void save(File file) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("name");
        fieldNames.add("ownerId");
        fieldNames.add("projectId");
        fieldNames.add("content");
        fieldNames.add("uploadTime");
        if (file.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, file),
                    DAOResponse.class
            );
        } else {
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_CREATE
                    ),
                    DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, file),
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
