package org.latheild.user.service;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.api.dto.ResetPasswordDTO;
import org.latheild.user.api.dto.UserDTO;
import org.latheild.user.api.dto.UserOperationDTO;

import java.util.ArrayList;

public interface UserService {
    UserDTO register(RegisterDTO registerDTO);

    UserDTO resetPassword(ResetPasswordDTO resetPasswordDTO);

    boolean checkPassword(RegisterDTO registerDTO);

    boolean checkUserExistence(String userId);

    UserDTO getUserByEmail(String email);

    UserDTO getUserByUserId(String id);

    ArrayList<UserDTO> getAllUsers();

    void adminDeleteUserByEmail(String email, String code);

    void adminDeleteUserByUserId(String id, String code);

    void adminDeleteAllUsers(String code);

    void addUserProject(UserOperationDTO userOperationDTO);

    void removeUserProject(UserOperationDTO userOperationDTO);

    ArrayList<UserDTO> getAllUsersByProjectId(String projectId);

    void addUserSchedule(UserOperationDTO userOperationDTO);

    void removeUserSchedule(UserOperationDTO userOperationDTO);

    ArrayList<UserDTO> getAllUsersByScheduleId(String scheduleId);

    void addUserTask(UserOperationDTO userOperationDTO);

    void removeUserTask(UserOperationDTO userOperationDTO);

    ArrayList<UserDTO> getAllUsersByTaskId(String taskId);
}
