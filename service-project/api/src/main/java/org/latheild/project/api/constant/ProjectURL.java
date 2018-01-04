package org.latheild.project.api.constant;

public interface ProjectURL {
    String CHECK_PROJECT_EXIST_URL = "/check";

    String CREATE_PROJECT_URL = "/create";

    String UPDATE_PROJECT_URL = "/update/info";

    String CHANGE_PROJECT_OWNER_URL = "/update/owner";

    String DELETE_PROJECT_BY_ID_URL = "/delete";

    String GET_PROJECT_BY_ID_URL = "/retrieve";

    String GET_PROJECTS_BY_USER_ID_URL = "/retrieve/all";

    String ADMIN_GET_ALL_PROJECTS_URL = "/admin/retrieve/all";

    String ADMIN_DELETE_PROJECT_BY_ID_URL = "/admin/delete";

    String ADMIN_DELETE_PROJECTS_URL = "/admin/delete/all";
}
