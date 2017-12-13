package userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.client.UserInfoServiceClient;
import userService.domain.Constants;
import userService.domain.User;
import userService.domain.UserInfoWrapper;
import userService.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoServiceClient userInfoServiceClient;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User user, UserInfoWrapper userInfoWrapper) {
        userInfoServiceClient.createUserInfoV3(
                userInfoWrapper
        );
        userRepository.save(user);
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
