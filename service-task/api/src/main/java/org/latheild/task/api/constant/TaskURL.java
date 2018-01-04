package org.latheild.task.api.constant;

public interface TaskURL {
    String CREATE_TASK_URL = "/create";

    String DELETE_TASK_BY_ID_URL = "/delete";

    String UPDATE_TASK_CONTENT_URL = "/content/update";

    String UPDATE_TASK_STATE_URL = "/state/update";

    String GET_TASK_BY_ID_URL = "/retrieve";

    String GET_TASKS_URL = "/retrieve/all";

    String ADMIN_GET_ALL_TASKS_URL = "/admin/retrieve/all";

    String ADMIN_DELETE_TASK_URL = "/admin/delete";

    String ADMIN_DELETE_ALL_TASKS_URL = "/admin/delete/all";
}
