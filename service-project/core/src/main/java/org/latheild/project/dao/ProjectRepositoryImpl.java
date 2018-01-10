package org.latheild.project.dao;

import org.latheild.common.domain.DAOResponse;
import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.project.domain.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.constant.BaseURI.*;

@SuppressWarnings("unchecked")
public class ProjectRepositoryImpl extends DAOResponseJSONAnalyzer implements ProjectRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/project";

    private Wrapper<Project> analyzer = new Wrapper<>(new Project());

    @Override
    public Project findById(String id) {
        return (Project) analyzer.analyze(
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
    public ArrayList<Project> findAllByOwnerId(String ownerId) {
        return (ArrayList<Project>) analyzer.analyze(
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
    public ArrayList<Project> findAll() {
        return (ArrayList<Project>) analyzer.analyze(
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
    public void deleteAllByOwnerId(String ownerID) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("OwnerId", ownerID),
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
    public void save(Project project) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("OwnerId");
        fieldNames.add("name");
        fieldNames.add("description");
        if (project.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, project),
                    DAOResponse.class
            );
        } else {
            Project response = (Project) analyzer.analyze(
                    restTemplate.postForObject(
                            CombineURI.combineURI(
                                    SERVICE_URI, BASE_CREATE
                            ),
                            DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, project),
                            DAOResponse.class
                    )
            );
            project.setId(response.getId());
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
