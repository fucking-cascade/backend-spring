package userService.domain;

import java.util.ArrayList;

public class UserReturnDataWrapper {
    private int id;
    private String email;
    private ArrayList<Integer> tasks;

    public UserReturnDataWrapper(int id, String email, ArrayList<Integer> tasks) {
        this.id = id;
        this.email = email;
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Integer> getTasks() {
        return tasks;
    }
}
