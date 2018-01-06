package org.latheild.user.api.constant;

public interface UserUrl {
    String CHECK_USER_EXIST_URL = "/check";

    String USER_REGISTER_URL = "/register";

    String USER_RESET_PASSWORD_URL = "/password/reset";

    String USER_CHECK_PASSWORD_URL = "/password/check";

    String GET_USER_URL = "/retrieve";

    String GET_USERS_URL = "/retrieve/all";

    String ADMIN_DELETE_USER_URL = "/admin/delete";

    String ADMIN_DELETE_ALL_USERS_URL = "/admin/delete/all";

    String USER_PARTICIPATE_PROJECT_URL = "/add/project";

    String USER_QUIT_PROJECT_URL = "/remove/project";

    String USER_PARTICIPATE_SCHEDULE_URL = "/add/schedule";

    String USER_QUIT_SCHEDULE_URL = "/remove/schedule";

    String USER_PARTICIPATE_TASK_URL = "/add/task";

    String USER_QUIT_TASK_URL = "/remove/task";
}
