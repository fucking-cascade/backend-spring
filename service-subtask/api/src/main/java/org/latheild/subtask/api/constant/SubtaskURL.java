package org.latheild.subtask.api.constant;

public interface SubtaskURL {
    public String CREATE_SUBTASK_URL = "/create";

    public String DELETE_SUBTASK_BY_ID_URL = "/delete";

    public String UPDATE_SUBTASK_CONTENT_URL = "/content/update";

    public String UPDATE_SUBTASK_STATE_URL = "/state/update";

    public String GET_SUBTASK_BY_ID_URL = "/retrieve";

    public String GET_SUBTASKS_URL = "/retrieve/all";

    public String ADMIN_GET_ALL_SUBTASKS_URL = "/admin/retrieve/all";

    public String ADMIN_DELETE_SUBTASK_URL = "/admin/delete";

    public String ADMIN_DELETE_ALL_SUBTASKS_URL = "/admin/delete/all";
}
