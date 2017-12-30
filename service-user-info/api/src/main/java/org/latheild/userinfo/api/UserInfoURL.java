package org.latheild.userinfo.api;

public interface UserInfoURL {
    String REGISTER_USER_INFO_URL = "/register";

    String UPDATE_USER_INFO_URL = "/update";

    String GET_USER_INFO_BY_ID_URL = "/retrieve";

    String GET_USER_INFOS_URL = "/retrieve/all";

    String GET_USER_INFOS_BY_NAME_URL = "/retrieve/all/name";

    String ADMIN_RESET_USER_INFO_BY_ID_URL = "/admin/reset";

    String ADMIN_DELETE_ALL_USER_INFOS_URL = "/admin/delete/all";

    String ADMIN_DELETE_USER_INFO_BY_ID_URL = "/admin/delete";
}
