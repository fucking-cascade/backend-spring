package taskService.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Task {
    @Id
    @NotNull
    private final int id;

    @NotBlank
    private String name;

    @NotBlank
    private String content;

    @NotNull
    private int ownerId;

    private TaskState taskState;

    private Priority priority;

    private Date date;

    private ArrayList<Integer> users;

    private ArrayList<Integer> subtasks;

    public Task(int id, String name, String content, int ownerId, TaskState taskState, Priority priority, Date date, ArrayList<Integer> users, ArrayList<Integer> subtasks) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.ownerId = ownerId;
        this.taskState = taskState;
        this.priority = priority;
        this.date = date;
        if (users == null) {
            this.users = new ArrayList<Integer>();
        } else {
            this.users = users;
        }
        if (subtasks == null) {
            this.subtasks = new ArrayList<Integer>();
        } else {
            this.subtasks = subtasks;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Integer> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Integer> users) {
        this.users = users;
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Integer> subtasks) {
        this.subtasks = subtasks;
    }

    public void addSubtask(int id) {
        this.subtasks.add(id);
    }

    public void addUser(int id) {
        this.users.add(id);
    }

    public void removeSubtask(int id) {
        for (Iterator iter = subtasks.iterator(); iter.hasNext();) {
            if (iter.next().equals(id)) {
                iter.remove();
                break;
            }
        }
    }

    public void removeUser(int id) {
        if (id == ownerId) {
            return;
        }
        for (Iterator iter = users.iterator(); iter.hasNext();) {
            if (iter.next().equals(id)) {
                iter.remove();
                break;
            }
        }
    }
}
