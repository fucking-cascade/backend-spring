package org.latheild.relation.core.dao;

import org.latheild.common.domain.DAOResponse;
import org.latheild.common.utils.CombineURI;
import org.latheild.common.utils.DAORequestJSONWrapper;
import org.latheild.common.utils.DAOResponseJSONAnalyzer;
import org.latheild.relation.core.domain.UserScheduleRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.latheild.common.constant.BaseURI.*;
import static org.latheild.common.constant.BaseURI.BASE_CREATE;

@SuppressWarnings("unchecked")
public class UserScheduleRelationRepositoryImpl extends DAOResponseJSONAnalyzer implements UserScheduleRelationRepository {
    @Autowired
    RestTemplate restTemplate;

    private String SERVICE_URI = "/scheduleuser";

    private Wrapper<UserScheduleRelation> analyzer = new Wrapper<>(new UserScheduleRelation());

    @Override
    public UserScheduleRelation findById(String id) {
        return (UserScheduleRelation) analyzer.analyze(
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
    public ArrayList<UserScheduleRelation> findAllByUserId(String userId) {
        return (ArrayList<UserScheduleRelation>) analyzer.analyze(
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
    public ArrayList<UserScheduleRelation> findAllByScheduleId(String scheduleId) {
        return (ArrayList<UserScheduleRelation>) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON("ScheduleId", scheduleId, DAORequestJSONWrapper.FIND_ALL),
                        DAOResponse.class
                )
        );
    }

    @Override
    public UserScheduleRelation findByUserIdAndScheduleId(String userId, String scheduleId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("ScheduleId");
        UserScheduleRelation userScheduleRelation = new UserScheduleRelation();
        userScheduleRelation.setScheduleId(scheduleId);
        userScheduleRelation.setUserId(userId);
        return (UserScheduleRelation) analyzer.analyze(
                restTemplate.postForObject(
                        CombineURI.combineURI(
                                SERVICE_URI, BASE_FIND
                        ),
                        DAORequestJSONWrapper.setFindRequestJSON(fieldNames, userScheduleRelation, DAORequestJSONWrapper.FIND_ONE),
                        DAOResponse.class
                )
        );
    }

    @Override
    public ArrayList<UserScheduleRelation> findAll() {
        return (ArrayList<UserScheduleRelation>) analyzer.analyze(
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
    public int countByScheduleId(String scheduleId) {
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON("ScheduleId", scheduleId),
                DAOResponse.class
        );
        if (checkError(daoResponse)) {
            return 0;
        } else {
            return (int) daoResponse.getData();
        }
    }

    @Override
    public int countByUserIdAndScheduleId(String userId, String scheduleId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("ScheduleId");
        UserScheduleRelation userScheduleRelation = new UserScheduleRelation();
        userScheduleRelation.setUserId(userId);
        userScheduleRelation.setScheduleId(scheduleId);
        DAOResponse daoResponse = restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_COUNT
                ),
                DAORequestJSONWrapper.setCountRequestJSON(fieldNames, userScheduleRelation),
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
    public void deleteAllByScheduleId(String scheduleId) {
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON("ScheduleId", scheduleId),
                DAOResponse.class
        );
    }

    @Override
    public void deleteByUserIdAndScheduleId(String userId, String scheduleId) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("ScheduleId");
        UserScheduleRelation userScheduleRelation = new UserScheduleRelation();
        userScheduleRelation.setUserId(userId);
        userScheduleRelation.setScheduleId(scheduleId);
        restTemplate.postForObject(
                CombineURI.combineURI(
                        SERVICE_URI, BASE_DELETE
                ),
                DAORequestJSONWrapper.setDeleteRequestJSON(fieldNames, userScheduleRelation),
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
    public void save(UserScheduleRelation userScheduleRelation) {
        ArrayList<String> fieldNames = new ArrayList<>();
        fieldNames.add("UserId");
        fieldNames.add("ScheduleId");
        if (userScheduleRelation.getId() != null) {
            fieldNames.add("id");
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_UPDATE
                    ),
                    DAORequestJSONWrapper.setUpdateRequestJSON(fieldNames, userScheduleRelation),
                    DAOResponse.class
            );
        } else {
            restTemplate.postForObject(
                    CombineURI.combineURI(
                            SERVICE_URI, BASE_CREATE
                    ),
                    DAORequestJSONWrapper.setCreateRequestJSON(fieldNames, userScheduleRelation),
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
