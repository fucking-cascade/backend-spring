package org.latheild.file.service;

import org.latheild.file.api.dto.FileDTO;

import java.util.ArrayList;

public interface FileService {
    boolean checkFileExistence(String fileId);

    FileDTO uploadFile(FileDTO fileDTO);

    FileDTO renameFile(FileDTO fileDTO);

    void deleteFileById(FileDTO fileDTO);

    FileDTO getFileById(String id);

    ArrayList<FileDTO> getFilesByOwnerId(String ownerId);

    ArrayList<FileDTO> getFilesByProjectId(String projectId);

    ArrayList<FileDTO> getFilesByOwnerIdAndProjectId(String ownerId, String projectId);

    ArrayList<FileDTO> adminGetAllFiles(String code);

    void adminDeleteFileById(String id, String code);

    void adminDeleteFilesByOwnerId(String ownerId, String code);

    void adminDeleteFilesByProjectId(String projectId, String code);

    void adminDeleteFilesByOwnerIdAndProjectId(String ownerId, String projectId, String code);

    void adminDeleteAllFiles(String code);
}
