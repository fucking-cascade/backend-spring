package org.latheild.schedule.service;

import org.latheild.schedule.api.dto.ScheduleDTO;

import java.util.ArrayList;

public interface ScheduleService {
    ScheduleDTO createSchedule(ScheduleDTO scheduleDTO);

    ScheduleDTO updateSchedule(ScheduleDTO scheduleDTO);

    void deleteScheduleById(ScheduleDTO scheduleDTO);

    ScheduleDTO getScheduleById(String id);

    ArrayList<ScheduleDTO> getSchedulesByOwnerId(String ownerId);

    ArrayList<ScheduleDTO> getSchedulesByProjectId(String projectId);

    ArrayList<ScheduleDTO> getSchedulesByOwnerIdAndProjectId(String ownerId, String projectId);

    ArrayList<ScheduleDTO> adminGetAllSchedules(String code);

    void adminDeleteScheduleById(String id, String code);

    void adminDeleteSchedulesByOwnerId(String ownerId, String code);

    void adminDeleteSchedulesByProjectId(String projectId, String code);

    void adminDeleteSchedulesByOwnerIdAndProjectId(String ownerId, String projectId, String code);

    void adminDeleteAllSchedules(String code);
}
