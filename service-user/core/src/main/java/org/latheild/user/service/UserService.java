package org.latheild.user.service;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.api.dto.ResetPasswordDTO;
import org.latheild.user.api.dto.UserDTO;

import java.util.ArrayList;

public interface UserService {
    public UserDTO register(RegisterDTO registerDTO);

    public UserDTO resetPassword(ResetPasswordDTO resetPasswordDTO);

    public boolean checkPassword(RegisterDTO registerDTO);

    public boolean checkUserExist(String userId);

    public UserDTO getUserByEmail(String email);

    public UserDTO getUserByUserId(String userId);

    public ArrayList<UserDTO> getAllUsers();

    public void adminDeleteUserByEmail(String email, String code);

    public void adminDeleteUserByUserId(String userId, String code);

    public void adminDeleteAllUsers(String code);
}
