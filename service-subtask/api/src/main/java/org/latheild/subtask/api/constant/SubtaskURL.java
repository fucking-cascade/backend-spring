package org.latheild.subtask.api.constant;

public interface SubtaskURL {
    String CREATE_SUBTASK_URL = "/create";

    String DELETE_SUBTASK_BY_ID_URL = "/delete";

    String UPDATE_SUBTASK_CONTENT_URL = "/update/content";

    String UPDATE_SUBTASK_STATE_URL = "/update/state";

    String GET_SUBTASK_BY_ID_URL = "/retrieve";

    String GET_SUBTASKS_URL = "/retrieve/all";

    String ADMIN_GET_ALL_SUBTASKS_URL = "/admin/retrieve/all";

    String ADMIN_DELETE_SUBTASK_URL = "/admin/delete";

    String ADMIN_DELETE_ALL_SUBTASKS_URL = "/admin/delete/all";
}
