package userService.domain;

public class ResetPasswordWrapper {
    int id;
    String oldPassword;
    String newPassword;

    public int getId() {
        return id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
