package org.latheild.progress.service;

import org.latheild.progress.api.dto.ProgressDTO;

import java.util.ArrayList;

public interface ProgressService {
    boolean checkProgressExistence(String progressId);

    ProgressDTO createProgress(ProgressDTO progressDTO);

    void deleteProgressById(ProgressDTO progressDTO);

    ProgressDTO updateProgressName(ProgressDTO progressDTO);

    ProgressDTO changeProgressOrder(ProgressDTO progressDTO);

    ProgressDTO getProgressById(String id);

    ArrayList<ProgressDTO> getProgressListByOwnerId(String ownerId);

    ArrayList<ProgressDTO> getProgressListByProjectId(String projectId);

    ArrayList<ProgressDTO> getProgressListByOwnerIdAndProjectId(String ownerId, String projectId);

    ArrayList<ProgressDTO> adminGetAllProgress(String code);

    void adminDeleteProgressById(String id, String code);

    void adminDeleteProgressByOwnerId(String ownerId, String code);

    void adminDeleteProgressByProjectId(String projectId, String code);

    void adminDeleteProgressByOwnerIdAndProjectId(String ownerId, String projectId, String code);

    void adminDeleteAllProgress(String code);
}
