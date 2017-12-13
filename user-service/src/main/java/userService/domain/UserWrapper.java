package userService.domain;

public class UserWrapper {
    private int id;
    private String email;
    private String password;
    private String name;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private String website;
    private String job;
    private String avatar;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public String getJob() {
        return job;
    }

    public String getAvatar() {
        return avatar;
    }
}
