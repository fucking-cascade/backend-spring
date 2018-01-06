package org.latheild.task.api.constant;

public interface TaskURL {
    String GET_PROJECT_ID_URL = "/retrieve/project";

    String CHECK_TASK_EXIST_URL = "/check";

    String CREATE_TASK_URL = "/create";

    String DELETE_TASK_BY_ID_URL = "/delete";

    String UPDATE_TASK_INFO_URL = "/update/info";

    String UPDATE_TASK_STATE_URL = "/update/state";

    String CHANGE_TASK_ORDER_URL = "/update/order";

    String CHANGE_TASK_PROGRESS_URL = "/update/progress";

    String GET_TASK_BY_ID_URL = "/retrieve";

    String GET_TASKS_URL = "/retrieve/all";

    String ADMIN_GET_ALL_TASKS_URL = "/admin/retrieve/all";

    String ADMIN_DELETE_TASK_BY_ID_URL = "/admin/delete";

    String ADMIN_DELETE_TASKS_URL = "/admin/delete/all";

    String ADD_TASK_PARTICIPANT_URL = "/add/participant";

    String REMOVE_TASK_PARTICIPANT_URL = "/remove/participant";

    String ADD_TASK_ATTACHMENT_URL = "/add/attachment";

    String REMOVE_TASK_ATTACHMENT_URL = "/remove/attachment";
}
