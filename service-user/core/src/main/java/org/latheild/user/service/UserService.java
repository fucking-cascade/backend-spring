package org.latheild.user.service;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.api.dto.ResetPasswordDTO;
import org.latheild.user.api.dto.UserDTO;

import java.util.ArrayList;

public interface UserService {
    UserDTO register(RegisterDTO registerDTO);

    UserDTO resetPassword(ResetPasswordDTO resetPasswordDTO);

    boolean checkPassword(RegisterDTO registerDTO);

    boolean checkUserExistence(String userId);

    UserDTO getUserByEmail(String email);

    UserDTO getUserByUserId(String userId);

    ArrayList<UserDTO> getAllUsers();

    void adminDeleteUserByEmail(String email, String code);

    void adminDeleteUserByUserId(String userId, String code);

    void adminDeleteAllUsers(String code);
}
