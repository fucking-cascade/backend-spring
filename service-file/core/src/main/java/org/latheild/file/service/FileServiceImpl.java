package org.latheild.file.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.common.utils.RabbitMQMessageCreator;
import org.latheild.common.constant.MessageType;
import org.latheild.common.domain.Message;
import org.latheild.file.api.constant.FileErrorCode;
import org.latheild.file.api.dto.AttachmentOperationDTO;
import org.latheild.file.api.dto.FileDTO;
import org.latheild.file.client.ProjectClient;
import org.latheild.file.client.RelationClient;
import org.latheild.file.client.TaskClient;
import org.latheild.file.client.UserClient;
import org.latheild.file.constant.DAOQueryMode;
import org.latheild.file.dao.FileRepository;
import org.latheild.file.domain.File;
import org.latheild.project.api.constant.ProjectErrorCode;
import org.latheild.relation.api.dto.RelationDTO;
import org.latheild.relation.api.utils.RelationDTOAnalyzer;
import org.latheild.task.api.constant.TaskErrorCode;
import org.latheild.user.api.constant.UserErrorCode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_CODE;
import static org.latheild.apiutils.constant.Constants.ADMIN_DELETE_ALL;
import static org.latheild.common.constant.RabbitMQExchange.FILE_FAN_OUT_EXCHANGE;
import static org.latheild.common.constant.RabbitMQQueue.FILE_QUEUE;

@Service
@RabbitListener(queues = FILE_QUEUE)
public class FileServiceImpl implements FileService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RelationClient relationClient;

    @Autowired
    UserClient userClient;

    @Autowired
    ProjectClient projectClient;

    @Autowired
    TaskClient taskClient;

    @Autowired
    FileRepository fileRepository;

    private boolean isFileExist(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                return (fileRepository.countById(target) > 0);
            case QUERY_BY_OWNER_ID:
                return (fileRepository.countByOwnerId(target) > 0);
            case QUERY_BY_PROJECT_ID:
                return (fileRepository.countByProjectId(target) > 0);
            default:
                throw new AppBusinessException(
                        CommonErrorCode.INTERNAL_ERROR
                );
        }
    }

    private FileDTO convertFromFileToFileDTO(File file) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setName(file.getName());
        fileDTO.setContent(file.getContent());
        fileDTO.setOwnerId(file.getUserId());
        fileDTO.setProjectId(file.getProjectId());
        fileDTO.setUploadTime(file.getUploadTime());
        fileDTO.setFileId(file.getId());
        return fileDTO;
    }

    private ArrayList<FileDTO> convertFromFilesToFileDTOs(ArrayList<File> files) {
        ArrayList<FileDTO> fileDTOs = new ArrayList<>();
        for (File file : files) {
            fileDTOs.add(convertFromFileToFileDTO(file));
        }
        return fileDTOs;
    }

    private File convertFromFileDTOToFile(FileDTO fileDTO) {
        File file = new File();
        file.setContent(fileDTO.getContent());
        file.setName(fileDTO.getName());
        file.setUserId(fileDTO.getOwnerId());
        file.setProjectId(fileDTO.getProjectId());
        file.setUploadTime(fileDTO.getUploadTime());
        return file;
    }

    @RabbitHandler
    public void eventHandler(Message message) {
        String messageBody;
        switch (message.getMessageType()) {
            case USER_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    if (fileRepository.count() > 0) {
                        adminDeleteAllFiles(ADMIN_CODE);
                    }
                } else {
                    if (isFileExist(DAOQueryMode.QUERY_BY_OWNER_ID, messageBody)) {
                        fileRepository.deleteAllByOwnerId(messageBody);
                    }
                }
                break;
            case PROJECT_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    if (fileRepository.count() > 0) {
                        adminDeleteAllFiles(ADMIN_CODE);
                    }
                } else {
                    if (isFileExist(DAOQueryMode.QUERY_BY_PROJECT_ID, messageBody)) {
                        fileRepository.deleteAllByProjectId(messageBody);
                    }
                }
                break;
            default:
                System.out.println(message.toString());
        }
    }

    @Override
    public boolean checkFileExistence(String fileId) {
        return isFileExist(DAOQueryMode.QUERY_BY_ID, fileId);
    }

    @Override
    public FileDTO uploadFile(FileDTO fileDTO) {
        if (userClient.checkUserExistence(fileDTO.getOwnerId())) {
            if (projectClient.checkProjectExistence(fileDTO.getProjectId())) {
                File file = convertFromFileDTOToFile(fileDTO);
                fileRepository.save(file);
                return convertFromFileToFileDTO(file);
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.PROJECT_NOT_EXIST,
                        String.format("Project %s does not exist", fileDTO.getProjectId())
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", fileDTO.getOwnerId())
            );
        }
    }

    @Override
    public FileDTO renameFile(FileDTO fileDTO) {
        if (isFileExist(DAOQueryMode.QUERY_BY_ID, fileDTO.getFileId())) {
            File file = fileRepository.findById(fileDTO.getFileId());
            if (file.getUserId().equals(fileDTO.getOwnerId())) {
                file.setName(fileDTO.getName());
                fileRepository.save(file);
                return convertFromFileToFileDTO(file);
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    FileErrorCode.FILE_NOT_EXIST,
                    String.format("File %s does not exist", fileDTO.getFileId())
            );
        }
    }

    @Override
    public void deleteFileById(FileDTO fileDTO) {
        if (isFileExist(DAOQueryMode.QUERY_BY_ID, fileDTO.getFileId())) {
            File file = fileRepository.findById(fileDTO.getFileId());
            if (file.getUserId().equals(fileDTO.getOwnerId())) {
                fileRepository.deleteById(file.getId());

                rabbitTemplate.convertAndSend(
                        FILE_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.FILE_DELETED,
                                file.getId()
                        )
                );
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    FileErrorCode.FILE_NOT_EXIST,
                    String.format("File %s does not exist", fileDTO.getFileId())
            );
        }
    }

    @Override
    public FileDTO getFileById(String id) {
        if (isFileExist(DAOQueryMode.QUERY_BY_ID, id)) {
            return convertFromFileToFileDTO(fileRepository.findById(id));
        } else {
            throw new AppBusinessException(
                    FileErrorCode.FILE_NOT_EXIST,
                    String.format("File %s does not exist", id)
            );
        }
    }

    @Override
    public ArrayList<FileDTO> getFilesByOwnerId(String ownerId) {
        if (isFileExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
            return convertFromFilesToFileDTOs(fileRepository.findAllByOwnerId(ownerId));
        } else {
            throw new AppBusinessException(
                    FileErrorCode.FILE_NOT_EXIST,
                    String.format("User %s does not have any file", ownerId)
            );
        }
    }

    @Override
    public ArrayList<FileDTO> getFilesByProjectId(String projectId) {
        if (isFileExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
            return convertFromFilesToFileDTOs(fileRepository.findAllByProjectId(projectId));
        } else {
            throw new AppBusinessException(
                    FileErrorCode.FILE_NOT_EXIST,
                    String.format("Project %s does not have any file", projectId)
            );
        }
    }

    @Override
    public ArrayList<FileDTO> getFilesByOwnerIdAndProjectId(String ownerId, String projectId) {
        if (isFileExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
            if (isFileExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
                return convertFromFilesToFileDTOs(fileRepository.findAllByOwnerIdAndProjectId(ownerId, projectId));
            } else {
                throw new AppBusinessException(
                        FileErrorCode.FILE_NOT_EXIST,
                        String.format("Project %s does not have any file assigned to user %s", projectId, ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    FileErrorCode.FILE_NOT_EXIST,
                    String.format("User %s does not have any file", ownerId)
            );
        }
    }

    @Override
    public ArrayList<FileDTO> adminGetAllFiles(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (fileRepository.count() > 0) {
                return convertFromFilesToFileDTOs(fileRepository.findAll());
            } else {
                throw new AppBusinessException(
                        FileErrorCode.FILE_NOT_EXIST
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteFileById(String id, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isFileExist(DAOQueryMode.QUERY_BY_ID, id)) {
                fileRepository.deleteById(id);

                rabbitTemplate.convertAndSend(
                        FILE_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.FILE_DELETED,
                                id
                        )
                );
            } else {
                throw new AppBusinessException(
                        FileErrorCode.FILE_NOT_EXIST,
                        String.format("File %s does not exist", id)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteFilesByOwnerId(String ownerId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isFileExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
                ArrayList<File> files = fileRepository.findAllByOwnerId(ownerId);
                fileRepository.deleteAllByOwnerId(ownerId);

                for (File file : files) {
                    rabbitTemplate.convertAndSend(
                            FILE_FAN_OUT_EXCHANGE,
                            "",
                            RabbitMQMessageCreator.newInstance(
                                    MessageType.FILE_DELETED,
                                    file.getId()
                            )
                    );
                }
            } else {
                throw new AppBusinessException(
                        FileErrorCode.FILE_NOT_EXIST,
                        String.format("User %s does not have any file", ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteFilesByProjectId(String projectId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isFileExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
                ArrayList<File> files = fileRepository.findAllByProjectId(projectId);
                fileRepository.deleteAllByProjectId(projectId);

                for (File file : files) {
                    rabbitTemplate.convertAndSend(
                            FILE_FAN_OUT_EXCHANGE,
                            "",
                            RabbitMQMessageCreator.newInstance(
                                    MessageType.FILE_DELETED,
                                    file.getId()
                            )
                    );
                }
            } else {
                throw new AppBusinessException(
                        FileErrorCode.FILE_NOT_EXIST,
                        String.format("Project %s does not have any file", projectId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteFilesByOwnerIdAndProjectId(String ownerId, String projectId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isFileExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
                if (isFileExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
                    ArrayList<File> files = fileRepository.findAllByOwnerIdAndProjectId(ownerId, projectId);
                    fileRepository.deleteAllByOwnerIdAndProjectId(ownerId, projectId);

                    for (File file : files) {
                        rabbitTemplate.convertAndSend(
                                FILE_FAN_OUT_EXCHANGE,
                                "",
                                RabbitMQMessageCreator.newInstance(
                                        MessageType.FILE_DELETED,
                                        file.getId()
                                )
                        );
                    }
                } else {
                    throw new AppBusinessException(
                            FileErrorCode.FILE_NOT_EXIST,
                            String.format("Project %s does not have any file assigned to user %s", projectId, ownerId)
                    );
                }
            } else {
                throw new AppBusinessException(
                        FileErrorCode.FILE_NOT_EXIST,
                        String.format("User %s does not have any file", ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteAllFiles(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (fileRepository.count() > 0) {
                fileRepository.deleteAll();

                rabbitTemplate.convertAndSend(
                        FILE_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.FILE_DELETED,
                                ADMIN_DELETE_ALL
                        )
                );
            } else {
                throw new AppBusinessException(
                        FileErrorCode.FILE_NOT_EXIST
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void attachFileToTask(AttachmentOperationDTO attachmentOperationDTO) {
        if (userClient.checkUserExistence(attachmentOperationDTO.getExecutorId())) {
            if (taskClient.checkTaskExistence(attachmentOperationDTO.getTaskId())) {
                if (isFileExist(DAOQueryMode.QUERY_BY_ID, attachmentOperationDTO.getFileId())) {
                    File file = fileRepository.findById(attachmentOperationDTO.getFileId());
                    if (file.getUserId().equals(attachmentOperationDTO.getExecutorId())) {
                        relationClient.addTaskAttachment(
                                attachmentOperationDTO.getFileId(),
                                attachmentOperationDTO.getTaskId()
                        );
                    } else {
                        throw new AppBusinessException(
                                CommonErrorCode.UNAUTHORIZED
                        );
                    }
                } else {
                    throw new AppBusinessException(
                            FileErrorCode.FILE_NOT_EXIST,
                            String.format("File %s does not exist", attachmentOperationDTO.getFileId())
                    );
                }
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST,
                        String.format("Task %s does not exist", attachmentOperationDTO.getTaskId())
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", attachmentOperationDTO.getExecutorId())
            );
        }
    }

    @Override
    public void detachFileToTask(AttachmentOperationDTO attachmentOperationDTO) {
        if (userClient.checkUserExistence(attachmentOperationDTO.getExecutorId())) {
            if (taskClient.checkTaskExistence(attachmentOperationDTO.getTaskId())) {
                if (isFileExist(DAOQueryMode.QUERY_BY_ID, attachmentOperationDTO.getFileId())) {
                    File file = fileRepository.findById(attachmentOperationDTO.getFileId());
                    if (file.getUserId().equals(attachmentOperationDTO.getExecutorId())) {
                        relationClient.deleteTaskAttachment(
                                attachmentOperationDTO.getFileId(),
                                attachmentOperationDTO.getTaskId()
                        );
                    } else {
                        throw new AppBusinessException(
                                CommonErrorCode.UNAUTHORIZED
                        );
                    }
                } else {
                    throw new AppBusinessException(
                            FileErrorCode.FILE_NOT_EXIST,
                            String.format("File %s does not exist", attachmentOperationDTO.getFileId())
                    );
                }
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST,
                        String.format("Task %s does not exist", attachmentOperationDTO.getTaskId())
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", attachmentOperationDTO.getExecutorId())
            );
        }
    }

    @Override
    public ArrayList<FileDTO> getAllFilesByTaskId(String taskId) {
        try {
            ArrayList<RelationDTO> relationDTOs = relationClient.getTaskAttachments(taskId);
            ArrayList<FileDTO> fileDTOS = new ArrayList<>();
            FileDTO fileDTO;
            for (RelationDTO relationDTO : relationDTOs) {
                fileDTO = getFileById(RelationDTOAnalyzer.getFileId(relationDTO));
                fileDTOS.add(fileDTO);
            }
            return fileDTOS;
        } catch (Exception e) {
            throw new AppBusinessException(
                    CommonErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }
}
