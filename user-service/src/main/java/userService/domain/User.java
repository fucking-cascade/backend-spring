package userService.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;

public class User {
    @Id
    @NotNull
    private final int id;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private ArrayList<Integer> tasks;

    public User(int id, String email, String password, ArrayList<Integer> tasks) {
        this.id = id;
        this.email = email;
        this.password = password;
        if (tasks == null) {
            this.tasks = new ArrayList<Integer>();
        } else {
            this.tasks = tasks;
        }
    }

    public int getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Integer> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Integer> tasks) {
        this.tasks = tasks;
    }

    public void update(User user) {
        this.email = user.email;
        this.password = user.password;
    }

    public void addTask(int id) {
        this.tasks.add(id);
    }

    public void removeTask(int id) {
        for (Iterator iter = this.tasks.iterator(); iter.hasNext();) {
            if (iter.next().equals(id)) {
                iter.remove();
                break;
            }
        }
    }
}
