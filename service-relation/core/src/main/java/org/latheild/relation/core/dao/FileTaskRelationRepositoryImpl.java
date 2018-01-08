package org.latheild.relation.core.dao;

import org.latheild.common.domain.DAOResponse;
import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.relation.core.domain.FileTaskRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.constant.BaseURI.*;
import static org.latheild.common.constant.BaseURI.BASE_CREATE;

@SuppressWarnings("unchecked")
public class FileTaskRelationRepositoryImpl extends DAOResponseJSONAnalyzer implements FileTaskRelationRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/taskfile";

    private Wrapper<FileTaskRelation> analyzer = new Wrapper<>(new FileTaskRelation());

    @Override
    public FileTaskRelation findById(String id) {
        return (FileTaskRelation) analyzer.analyze(
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
    public ArrayList<FileTaskRelation> findAllByFileId(String fileId) {
        return (ArrayList<FileTaskRelation>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON("FileId", fileId, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<FileTaskRelation> findAllByTaskId(String taskId) {
        return (ArrayList<FileTaskRelation>) analyzer.analyze(
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
    public FileTaskRelation findByFileIdAndTaskId(String fileId, String taskId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("FileId");
        fieldNames.add("TaskId");
        FileTaskRelation fileTaskRelation = new FileTaskRelation();
        fileTaskRelation.setTaskId(taskId);
        fileTaskRelation.setFileId(fileId);
        return (FileTaskRelation) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON(fieldNames, fileTaskRelation, DAORequestJSONWrapper.FIND_ONE),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<FileTaskRelation> findAll() {
        return (ArrayList<FileTaskRelation>) analyzer.analyze(
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
    public int countByFileId(String fileId) {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON("FileId", fileId),
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
    public int countByFileIdAndTaskId(String fileId, String taskId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("FileId");
        fieldNames.add("TaskId");
        FileTaskRelation fileTaskRelation = new FileTaskRelation();
        fileTaskRelation.setTaskId(taskId);
        fileTaskRelation.setFileId(fileId);
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON(fieldNames, fileTaskRelation),
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
    public void deleteAllByFileId(String fileId) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("FileId", fileId),
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
    public void deleteByFileIdAndTaskId(String fileId, String taskId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("FileId");
        fieldNames.add("TaskId");
        FileTaskRelation fileTaskRelation = new FileTaskRelation();
        fileTaskRelation.setTaskId(taskId);
        fileTaskRelation.setFileId(fileId);
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON(fieldNames, fileTaskRelation),
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
    public void save(FileTaskRelation fileTaskRelation) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("FileId");
        fieldNames.add("TaskId");
        if (fileTaskRelation.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, fileTaskRelation),
                    DAOResponse.class
            );
        } else {
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_CREATE
                    ),
                    DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, fileTaskRelation),
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
