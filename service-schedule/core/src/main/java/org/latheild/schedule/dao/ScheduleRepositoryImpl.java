package org.latheild.schedule.dao;

import org.latheild.common.domain.DAOResponse;
import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.schedule.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.constant.BaseURI.*;

@SuppressWarnings("unchecked")
public class ScheduleRepositoryImpl extends DAOResponseJSONAnalyzer implements ScheduleRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/schedule";

    private Wrapper<Schedule> analyzer = new Wrapper<>(new Schedule());

    @Override
    public Schedule findById(String id) {
        return (Schedule) analyzer.analyze(
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
    public ArrayList<Schedule> findAllByOwnerId(String ownerId) {
        return (ArrayList<Schedule>) analyzer.analyze(
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
    public ArrayList<Schedule> findAllByProjectId(String projectId) {
        return (ArrayList<Schedule>) analyzer.analyze(
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
    public ArrayList<Schedule> findAllByOwnerIdAndProjectId(String ownerId, String projectId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("ownerId");
        fieldNames.add("projectId");
        Schedule schedule = new Schedule();
        schedule.setOwnerId(ownerId);
        schedule.setProjectId(projectId);
        return (ArrayList<Schedule>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON(fieldNames, schedule, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<Schedule> findAll() {
        return (ArrayList<Schedule>) analyzer.analyze(
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
                DAORequestJSONWrapper.setCountRequestJSON("OwnerId", projectId),
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
        Schedule schedule = new Schedule();
        schedule.setOwnerId(ownerId);
        schedule.setProjectId(projectId);
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON(fieldNames, schedule),
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
    public void save(Schedule schedule) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("name");
        fieldNames.add("OwnerId");
        fieldNames.add("ProjectId");
        fieldNames.add("content");
        fieldNames.add("startTime");
        fieldNames.add("endTime");
        fieldNames.add("location");
        if (schedule.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, schedule),
                    DAOResponse.class
            );
        } else {
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_CREATE
                    ),
                    DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, schedule),
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
