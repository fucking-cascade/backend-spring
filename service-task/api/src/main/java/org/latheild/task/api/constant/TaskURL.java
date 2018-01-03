package org.latheild.task.api.constant;

public interface TaskURL {
    public String CREATE_TASK_URL = "/create";

    public String DELETE_TASK_BY_ID_URL = "/delete";

    public String UPDATE_TASK_CONTENT_URL = "/content/update";

    public String UPDATE_TASK_STATE_URL = "/state/update";

    public String GET_TASK_BY_ID_URL = "/retrieve";

    public String GET_TASKS_URL = "/retrieve/all";

    public String ADMIN_GET_ALL_TASKS_URL = "/admin/retrieve/all";

    public String ADMIN_DELETE_TASK_URL = "/admin/delete";

    public String ADMIN_DELETE_ALL_TASKS_URL = "/admin/delete/all";
}
