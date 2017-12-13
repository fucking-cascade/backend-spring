package taskService.domain;

import java.util.ArrayList;
import java.util.Date;

public class TaskWrapper {
    private int id;
    private String name;
    private String content;
    private int ownerId;
    private TaskState taskState;
    private Priority priority;
    private Date date;
    private ArrayList<Integer> users;
    private ArrayList<Integer> subtasks;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public Priority getPriority() {
        return priority;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Integer> getUsers() {
        return users;
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }
}
