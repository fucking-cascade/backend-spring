package userService.service;

import userService.domain.User;
import userService.domain.UserInfoWrapper;

import java.util.ArrayList;

public interface UserService {
    public Object createUser(User user, UserInfoWrapper userInfoWrapper);

    public void updateUser(User user);

    public String getUserEmailById(int id);

    public void deleteUser();

    public Boolean checkLogin(int id, String password);

    public void resetPassword(int id, String password);

    public void resetEmail(int id, String email);

    public int generateNewId();
}
