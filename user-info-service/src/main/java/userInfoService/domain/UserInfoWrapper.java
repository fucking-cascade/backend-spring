package userInfoService.domain;

public class UserInfoWrapper {
    private int id;
    private String name;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private String website;
    private String job;
    private String avatar;

    public UserInfoWrapper() {
    }

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public Gender getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebsite() {
        return website;
    }
}
