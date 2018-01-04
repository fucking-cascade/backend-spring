package org.latheild.progress.api.constant;

public interface ProgressURL {
    String CHECK_PROGRESS_EXIST_URL = "/check";

    String CREATE_PROGRESS_URL = "/create";

    String DELETE_PROGRESS_BY_ID_URL = "/delete";

    String UPDATE_PROGRESS_NAME_URL = "/update/name";

    String CHANGE_PROGRESS_ORDER_URL = "/update/order";

    String GET_PROGRESS_BY_ID_URL = "/retrieve";

    String GET_PROGRESS_LIST_URL = "/retrieve/all";

    String ADMIN_GET_ALL_PROGRESS_URL = "/admin/retrieve/all";

    String ADMIN_DELETE_PROGRESS_BY_ID_URL = "/admin/delete";

    String ADMIN_DELETE_ALL_PROGRESS_URL = "/admin/delete/all";
}
