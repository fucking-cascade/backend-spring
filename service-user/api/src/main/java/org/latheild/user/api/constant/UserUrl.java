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
}
