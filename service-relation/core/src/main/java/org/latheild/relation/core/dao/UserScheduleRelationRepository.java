package org.latheild.relation.core.dao;

import org.latheild.relation.core.domain.UserScheduleRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface UserScheduleRelationRepository extends MongoRepository<UserScheduleRelation, String> {
    UserScheduleRelation findById(String id);

    ArrayList<UserScheduleRelation> findAllByUserId(String userId);

    ArrayList<UserScheduleRelation> findAllByScheduleId(String scheduleId);

    UserScheduleRelation findByUserIdAndScheduleId(String userId, String scheduleId);

    ArrayList<UserScheduleRelation> findAll();

    int countById(String id);

    int countByUserId(String userId);

    int countByScheduleId(String scheduleId);

    int countByUserIdAndScheduleId(String userId, String scheduleId);

    void deleteById(String id);

    void deleteAllByUserId(String userId);

    void deleteAllByScheduleId(String scheduleId);

    void deleteByUserIdAndScheduleId(String userId, String scheduleId);
}
