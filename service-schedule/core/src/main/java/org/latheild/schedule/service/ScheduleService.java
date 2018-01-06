package org.latheild.schedule.service;

import org.latheild.schedule.api.dto.ScheduleDTO;
import org.latheild.schedule.api.dto.ScheduleParticipantOperationDTO;

import java.util.ArrayList;

public interface ScheduleService {
    boolean checkScheduleExistence(String scheduleId);

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

    void addScheduleParticipant(ScheduleParticipantOperationDTO scheduleParticipantOperationDTO);

    void removeScheduleParticipant(ScheduleParticipantOperationDTO scheduleParticipantOperationDTO);

    ArrayList<ScheduleDTO> getAllSchedulesByUserId(String userId);
}
