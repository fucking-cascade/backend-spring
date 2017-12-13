package userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.client.TaskServiceClient;
import userService.client.UserInfoServiceClient;
import userService.domain.*;
import userService.repository.UserRepository;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TaskServiceClient taskServiceClient;

    @Autowired
    private UserInfoServiceClient userInfoServiceClient;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object createUser(User user, UserInfoWrapper userInfoWrapper) {
        Integer ownerId = user.getId();
        user.addTask(taskServiceClient.createTutorialV1(ownerId));

        UserDataWrapper userDataWrapper = new UserDataWrapper(
                new UserReturnDataWrapper(
                        user.getId(),
                        user.getEmail(),
                        user.getTasks()
                ),
                userInfoServiceClient.createUserInfoV3(userInfoWrapper)
        );

        userRepository.save(user);
        return userDataWrapper;
    }

    @Override
    public void updateUser(User user) {
        User tempUser = userRepository.findById(user.getId());
        tempUser.update(user);
        userRepository.save(tempUser);
    }

    @Override
    public String getUserEmailById(int id) {
        User user = userRepository.findById(id);
        return user.getEmail();
    }

    @Override
    public void deleteUser() {
        userRepository.deleteAll();
        userInfoServiceClient.deleteUserInfo();
    }

    @Override
    public Boolean checkLogin(int id, String password) {
        User user = userRepository.findById(id);
        if (user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void resetPassword(int id, String password) {
        User user = userRepository.findById(id);
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public void resetEmail(int id, String email) {
        User user = userRepository.findById(id);
        user.setEmail(email);
        userRepository.save(user);
    }

    @Override
    public int generateNewId() {
        int newId = Constants.RESERVED_NUM_OF_IDS + (int)userRepository.count();
        return newId;
    }
}
