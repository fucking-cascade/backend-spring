package org.latheild.user.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.common.api.RabbitMQMessageCreator;
import org.latheild.common.constant.MessageType;
import org.latheild.user.api.constant.UserErrorCode;
import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.api.dto.ResetPasswordDTO;
import org.latheild.user.api.dto.UserDTO;
import org.latheild.user.client.UserInfoClient;
import org.latheild.user.constant.DAOQueryMode;
import org.latheild.user.dao.UserRepository;
import org.latheild.user.domain.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_CODE;
import static org.latheild.apiutils.constant.Constants.ADMIN_DELETE_ALL;
import static org.latheild.common.constant.RabbitMQExchange.USER_FAN_OUT_EXCHANGE;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserInfoClient userInfoClient;

    @Autowired
    private UserRepository userRepository;

    private boolean isUserCreated(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                return (userRepository.countById(target) > 0);
            case QUERY_BY_EMAIL:
                return (userRepository.countByEmail(target) > 0);
            default:
                throw new AppBusinessException(
                        CommonErrorCode.INTERNAL_ERROR
                );
        }
    }

    private UserDTO convertFromUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUserId(user.getId());
        return userDTO;
    }

    private ArrayList<UserDTO> convertFromUsersToUserDTOs(ArrayList<User> users) {
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(convertFromUserToUserDTO(user));
        }
        return userDTOs;
    }

    @Override
    public UserDTO register(RegisterDTO registerDTO) {
        if (!isUserCreated(DAOQueryMode.QUERY_BY_EMAIL, registerDTO.getEmail())) {
            User user = new User();
            user.setEmail(registerDTO.getEmail());
            user.setPassword(registerDTO.getPassword());
            userRepository.save(user);

            registerDTO.setUserId(user.getId());
            rabbitTemplate.convertAndSend(
                    USER_FAN_OUT_EXCHANGE,
                    "",
                    RabbitMQMessageCreator.newInstance(MessageType.USER_CREATED, registerDTO)
            );

            return convertFromUserToUserDTO(user);
        } else {
            throw new AppBusinessException(
                    UserErrorCode.EmailExist,
                    String.format("Email %s has already been used", registerDTO.getEmail())
            );
        }
    }

    @Override
    public UserDTO resetPassword(ResetPasswordDTO resetPasswordDTO) {
        if (isUserCreated(DAOQueryMode.QUERY_BY_ID, resetPasswordDTO.getUserId())) {
            User user = userRepository.findById(resetPasswordDTO.getUserId());
            if (user.getPassword().equals(resetPasswordDTO.getOldPassword())) {
                user.setPassword(resetPasswordDTO.getNewPassword());
                userRepository.save(user);
                return convertFromUserToUserDTO(user);
            } else {
                throw new AppBusinessException(
                        UserErrorCode.WrongPassword
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.UserNotExist,
                    String.format("User %s does not exist", resetPasswordDTO.getUserId())
            );
        }
    }

    @Override
    public boolean checkPassword(RegisterDTO registerDTO) {
        User user;
        if (isUserCreated(DAOQueryMode.QUERY_BY_EMAIL, registerDTO.getEmail())) {
            user = userRepository.findByEmail(registerDTO.getEmail());
        } else if (isUserCreated(DAOQueryMode.QUERY_BY_ID, registerDTO.getUserId())) {
            user = userRepository.findById(registerDTO.getUserId());
        } else {
            throw new AppBusinessException(
                    UserErrorCode.UserNotExist,
                    String.format("User %s (userId: %s) does not exist", registerDTO.getEmail(), registerDTO.getUserId())
            );
        }
        return user.getPassword().equals(registerDTO.getPassword());
    }

    @Override
    public boolean checkUserExistence(String userId) {
        return isUserCreated(DAOQueryMode.QUERY_BY_ID, userId);
    }


    @Override
    public UserDTO getUserByEmail(String email) {
        if (isUserCreated(DAOQueryMode.QUERY_BY_EMAIL, email)) {
            return convertFromUserToUserDTO(userRepository.findByEmail(email));
        } else {
            throw new AppBusinessException(
                    UserErrorCode.UserNotExist,
                    String.format("User %s does not exist", email)
            );
        }
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        if (isUserCreated(DAOQueryMode.QUERY_BY_ID, userId)) {
            return convertFromUserToUserDTO(userRepository.findById(userId));
        } else {
            throw new AppBusinessException(
                    UserErrorCode.UserNotExist,
                    String.format("User %s does not exist", userId)
            );
        }
    }

    @Override
    public ArrayList<UserDTO> getAllUsers() {
        if (userRepository.count() > 0) {
            return convertFromUsersToUserDTOs(userRepository.findAll());
        } else {
            throw new AppBusinessException(
                    UserErrorCode.UserNotExist
            );
        }
    }

    @Override
    public void adminDeleteUserByEmail(String email, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isUserCreated(DAOQueryMode.QUERY_BY_EMAIL, email)) {
                UserDTO userDTO = getUserByEmail(email);
                userRepository.deleteByEmail(email);

                rabbitTemplate.convertAndSend(
                        USER_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(MessageType.USER_DELETED, userDTO.getUserId())
                );
            } else {
                throw new AppBusinessException(
                        UserErrorCode.UserNotExist,
                        String.format("User %s does not exist", email)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteUserByUserId(String userId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isUserCreated(DAOQueryMode.QUERY_BY_ID, userId)) {
                userRepository.deleteById(userId);

                rabbitTemplate.convertAndSend(
                        USER_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(MessageType.USER_DELETED, userId)
                );
            } else {
                throw new AppBusinessException(
                        UserErrorCode.UserNotExist,
                        String.format("User %s does not exist", userId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteAllUsers(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (userRepository.count() > 0) {
                userRepository.deleteAll();

                rabbitTemplate.convertAndSend(
                        USER_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(MessageType.USER_DELETED, ADMIN_DELETE_ALL)
                );
            } else {
                throw new AppBusinessException(
                        UserErrorCode.UserNotExist
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }
}
