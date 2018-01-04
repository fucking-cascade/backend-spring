package org.latheild.task.api.constant;

public interface TaskURL {
    String CHECK_TASK_EXIST_URL = "/check";

    String CREATE_TASK_URL = "/create";

    String DELETE_TASK_BY_ID_URL = "/delete";

    String UPDATE_TASK_INFO_URL = "/update/info";

    String UPDATE_TASK_STATE_URL = "/update/state";

    String CHANGE_TASK_ORDER_URL = "/change/order";

    String CHANGE_TASK_PROGRESS_URL = "/change/progress";

    String GET_TASK_BY_ID_URL = "/retrieve";

    String GET_TASKS_URL = "/retrieve/all";

    String ADMIN_GET_ALL_TASKS_URL = "/admin/retrieve/all";

    String ADMIN_DELETE_TASK_BY_ID_URL = "/admin/delete";

    String ADMIN_DELETE_TASKS_URL = "/admin/delete/all";
}
