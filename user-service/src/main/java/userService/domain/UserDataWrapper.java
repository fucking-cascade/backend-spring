package userService.domain;

public class UserDataWrapper {
    private Object user;
    private Object userInfo;

    public UserDataWrapper(Object user, Object userInfo) {
        this.user = user;
        this.userInfo = userInfo;
    }

    public Object getUser() {
        return user;
    }

    public Object getUserInfo() {
        return userInfo;
    }
}
