package org.latheild.task.domain;

import org.latheild.common.api.CommonTaskStatus;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class Task {
    @Id
    @NotNull
    private String id;

    private String ownerId;

    private String executorId;

    private String progressId;

    private String name;

    private String content;

    private CommonTaskStatus taskStatus;
}
