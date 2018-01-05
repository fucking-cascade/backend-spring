package org.latheild.schedule.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.common.api.RabbitMQMessageCreator;
import org.latheild.common.constant.MessageType;
import org.latheild.common.domain.Message;
import org.latheild.project.api.constant.ProjectErrorCode;
import org.latheild.schedule.api.constant.ScheduleErrorCode;
import org.latheild.schedule.api.dto.ScheduleDTO;
import org.latheild.schedule.client.ProjectClient;
import org.latheild.schedule.client.UserClient;
import org.latheild.schedule.constant.DAOQueryMode;
import org.latheild.schedule.dao.ScheduleRepository;
import org.latheild.schedule.domain.Schedule;
import org.latheild.user.api.constant.UserErrorCode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_CODE;
import static org.latheild.apiutils.constant.Constants.ADMIN_DELETE_ALL;
import static org.latheild.common.constant.RabbitMQExchange.SCHEDULE_FAN_OUT_EXCHANGE;
import static org.latheild.common.constant.RabbitMQQueue.SCHEDULE_QUEUE;

@Service
@RabbitListener(queues = SCHEDULE_QUEUE)
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserClient userClient;

    @Autowired
    ProjectClient projectClient;

    @Autowired
    ScheduleRepository scheduleRepository;

    private boolean isScheduleExist(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                return (scheduleRepository.countById(target) > 0);
            case QUERY_BY_OWNER_ID:
                return (scheduleRepository.countByOwnerId(target) > 0);
            case QUERY_BY_PROJECT_ID:
                return (scheduleRepository.countByProjectId(target) > 0);
            default:
                throw new AppBusinessException(
                        CommonErrorCode.INTERNAL_ERROR
                );
        }
    }

    private ScheduleDTO convertFromScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setContent(schedule.getContent());
        scheduleDTO.setEndTime(schedule.getEndTime());
        scheduleDTO.setLocation(schedule.getLocation());
        scheduleDTO.setName(schedule.getName());
        scheduleDTO.setOwnerId(schedule.getOwnerId());
        scheduleDTO.setProjectId(schedule.getProjectId());
        scheduleDTO.setStartTime(schedule.getStartTime());
        scheduleDTO.setScheduleId(schedule.getId());
        return scheduleDTO;
    }

    private ArrayList<ScheduleDTO> convertFromSchedulesToScheduleDTOs(ArrayList<Schedule> schedules) {
        ArrayList<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(convertFromScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    private Schedule convertFromScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setContent(scheduleDTO.getContent());
        schedule.setEndTime(scheduleDTO.getEndTime());
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setName(scheduleDTO.getName());
        schedule.setLocation(scheduleDTO.getLocation());
        schedule.setProjectId(scheduleDTO.getProjectId());
        schedule.setOwnerId(scheduleDTO.getOwnerId());
        return schedule;
    }

    @RabbitHandler
    public void eventHandler(Message message) {
        String messageBody;
        switch (message.getMessageType()) {
            case USER_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    if (scheduleRepository.count() > 0) {
                        adminDeleteAllSchedules(ADMIN_CODE);
                    }
                } else {
                    if (isScheduleExist(DAOQueryMode.QUERY_BY_OWNER_ID, messageBody)) {
                        scheduleRepository.deleteAllByOwnerId(messageBody);
                    }
                }
                break;
            case PROJECT_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    if (scheduleRepository.count() > 0) {
                        adminDeleteAllSchedules(ADMIN_CODE);
                    }
                } else {
                    if (isScheduleExist(DAOQueryMode.QUERY_BY_PROJECT_ID, messageBody)) {
                        scheduleRepository.deleteAllByProjectId(messageBody);
                    }
                }
                break;
            default:
                System.out.println(message.toString());
        }
    }

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        if (userClient.checkUserExistence(scheduleDTO.getOwnerId())) {
            if (projectClient.checkProjectExistence(scheduleDTO.getProjectId())) {
                Schedule schedule = convertFromScheduleDTOToSchedule(scheduleDTO);
                scheduleRepository.save(schedule);
                return convertFromScheduleToScheduleDTO(schedule);
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.PROJECT_NOT_EXIST,
                        String.format("Project %s does not exist", scheduleDTO.getProjectId())
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", scheduleDTO.getOwnerId())
            );
        }
    }

    @Override
    public ScheduleDTO updateSchedule(ScheduleDTO scheduleDTO) {
        if (isScheduleExist(DAOQueryMode.QUERY_BY_ID, scheduleDTO.getScheduleId())) {
            Schedule schedule = scheduleRepository.findById(scheduleDTO.getScheduleId());
            if (schedule.getOwnerId().equals(scheduleDTO.getOwnerId())) {
                Schedule updatedSchedule = convertFromScheduleDTOToSchedule(scheduleDTO);
                updatedSchedule.setId(schedule.getId());
                scheduleRepository.save(updatedSchedule);
                return convertFromScheduleToScheduleDTO(updatedSchedule);
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                    String.format("Schedule %s does not exist", scheduleDTO.getProjectId())
            );
        }
    }

    @Override
    public void deleteScheduleById(ScheduleDTO scheduleDTO) {
        if (isScheduleExist(DAOQueryMode.QUERY_BY_ID, scheduleDTO.getScheduleId())) {
            Schedule schedule = scheduleRepository.findById(scheduleDTO.getScheduleId());
            if (schedule.getOwnerId().equals(scheduleDTO.getOwnerId())) {
                scheduleRepository.deleteById(scheduleDTO.getScheduleId());

                rabbitTemplate.convertAndSend(
                        SCHEDULE_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.SCHEDULE_DELETED,
                                schedule.getId()
                        )
                );
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                    String.format("Schedule %s does not exist", scheduleDTO.getProjectId())
            );
        }
    }

    @Override
    public ScheduleDTO getScheduleById(String id) {
        if (isScheduleExist(DAOQueryMode.QUERY_BY_ID, id)) {
            return convertFromScheduleToScheduleDTO(scheduleRepository.findById(id));
        } else {
            throw new AppBusinessException(
                    ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                    String.format("Schedule %s does not exist", id)
            );
        }
    }

    @Override
    public ArrayList<ScheduleDTO> getSchedulesByOwnerId(String ownerId) {
        if (isScheduleExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
            return convertFromSchedulesToScheduleDTOs(scheduleRepository.findAllByOwnerId(ownerId));
        } else {
            throw new AppBusinessException(
                    ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                    String.format("User %s does not have any schedule", ownerId)
            );
        }
    }

    @Override
    public ArrayList<ScheduleDTO> getSchedulesByProjectId(String projectId) {
        if (isScheduleExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
            return convertFromSchedulesToScheduleDTOs(scheduleRepository.findAllByProjectId(projectId));
        } else {
            throw new AppBusinessException(
                    ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                    String.format("Project %s does not have any schedule", projectId)
            );
        }
    }

    @Override
    public ArrayList<ScheduleDTO> getSchedulesByOwnerIdAndProjectId(String ownerId, String projectId) {
        if (isScheduleExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
            if (isScheduleExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
                return convertFromSchedulesToScheduleDTOs(scheduleRepository.findAllByOwnerIdAndProjectId(ownerId, projectId));
            } else {
                throw new AppBusinessException(
                        UserErrorCode.USER_NOT_EXIST,
                        String.format("Project %s does not have any schedule assigned to user %s", projectId, ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                    String.format("User %s does not have any schedule", ownerId)
            );
        }
    }

    @Override
    public ArrayList<ScheduleDTO> adminGetAllSchedules(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (scheduleRepository.count() > 0) {
                return convertFromSchedulesToScheduleDTOs(scheduleRepository.findAll());
            } else {
                throw new AppBusinessException(
                        ScheduleErrorCode.SCHEDULE_NOT_EXIST
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteScheduleById(String id, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isScheduleExist(DAOQueryMode.QUERY_BY_ID, id)) {
                scheduleRepository.deleteById(id);

                rabbitTemplate.convertAndSend(
                        SCHEDULE_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.SCHEDULE_DELETED,
                                id
                        )
                );
            } else {
                throw new AppBusinessException(
                        ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                        String.format("Schedule %s does not exist", id)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteSchedulesByOwnerId(String ownerId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isScheduleExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
                ArrayList<Schedule> schedules = scheduleRepository.findAllByOwnerId(ownerId);
                scheduleRepository.deleteAllByOwnerId(ownerId);

                for (Schedule schedule : schedules) {
                    rabbitTemplate.convertAndSend(
                            SCHEDULE_FAN_OUT_EXCHANGE,
                            "",
                            RabbitMQMessageCreator.newInstance(
                                    MessageType.SCHEDULE_DELETED,
                                    schedule.getId()
                            )
                    );
                }
            } else {
                throw new AppBusinessException(
                        ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                        String.format("User %s does not have any schedule", ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteSchedulesByProjectId(String projectId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isScheduleExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
                ArrayList<Schedule> schedules = scheduleRepository.findAllByProjectId(projectId);
                scheduleRepository.deleteAllByProjectId(projectId);

                for (Schedule schedule : schedules) {
                    rabbitTemplate.convertAndSend(
                            SCHEDULE_FAN_OUT_EXCHANGE,
                            "",
                            RabbitMQMessageCreator.newInstance(
                                    MessageType.SCHEDULE_DELETED,
                                    schedule.getId()
                            )
                    );
                }
            } else {
                throw new AppBusinessException(
                        ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                        String.format("Project %s does not have any schedule", projectId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteSchedulesByOwnerIdAndProjectId(String ownerId, String projectId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isScheduleExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
                if (isScheduleExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
                    ArrayList<Schedule> schedules = scheduleRepository.findAllByOwnerIdAndProjectId(ownerId, projectId);
                    scheduleRepository.deleteAllByOwnerIdAndProjectId(ownerId, projectId);

                    for (Schedule schedule : schedules) {
                        rabbitTemplate.convertAndSend(
                                SCHEDULE_FAN_OUT_EXCHANGE,
                                "",
                                RabbitMQMessageCreator.newInstance(
                                        MessageType.SCHEDULE_DELETED,
                                        schedule.getId()
                                )
                        );
                    }
                } else {
                    throw new AppBusinessException(
                            UserErrorCode.USER_NOT_EXIST,
                            String.format("Project %s does not have any schedule assigned to user %s", projectId, ownerId)
                    );
                }
            } else {
                throw new AppBusinessException(
                        ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                        String.format("User %s does not have any schedule", ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteAllSchedules(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (scheduleRepository.count() > 0) {
                scheduleRepository.deleteAll();

                rabbitTemplate.convertAndSend(
                        SCHEDULE_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.SCHEDULE_DELETED,
                                ADMIN_DELETE_ALL
                        )
                );
            } else {
                throw new AppBusinessException(
                        ScheduleErrorCode.SCHEDULE_NOT_EXIST
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }
}
