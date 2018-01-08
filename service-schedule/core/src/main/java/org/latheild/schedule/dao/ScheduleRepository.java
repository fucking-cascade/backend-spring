package org.latheild.schedule.dao;

import org.latheild.schedule.domain.Schedule;

import java.util.ArrayList;

public interface ScheduleRepository {
    Schedule findById(String id);

    ArrayList<Schedule> findAllByOwnerId(String ownerId);

    ArrayList<Schedule> findAllByProjectId(String projectId);

    ArrayList<Schedule> findAllByOwnerIdAndProjectId(String ownerId, String projectId);

    ArrayList<Schedule> findAll();

    int countById(String id);

    int countByOwnerId(String ownerId);

    int countByProjectId(String projectId);

    void deleteById(String id);

    void deleteAllByOwnerId(String ownerId);

    void deleteAllByProjectId(String projectId);

    void deleteAllByOwnerIdAndProjectId(String ownerId, String projectId);

    void deleteAll();

    void save(Schedule schedule);

    int count();
}
