package org.latheild.schedule.domain;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class Schedule {
    @Id
    @NotNull
    private String id;

    private String name;

    private String ProjectId;

    private String OwnerId;

    private String content;

    private String startTime;

    private String endTime;

    private String location;

    private Boolean repeatDaily;

    private Boolean repeatWeekly;

    private String createdAt;

    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getRepeatDaily() {
        return repeatDaily;
    }

    public void setRepeatDaily(Boolean repeatDaily) {
        this.repeatDaily = repeatDaily;
    }

    public Boolean getRepeatWeekly() {
        return repeatWeekly;
    }

    public void setRepeatWeekly(Boolean repeatWeekly) {
        this.repeatWeekly = repeatWeekly;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
