package org.latheild.project.api.constant;

public interface ProjectURL {
    public String CREATE_PROJECT_URL = "/create";

    public String UPDATE_PROJECT_URL = "/update/info";

    public String CHANGE_PROJECT_OWNER_URL = "/update/owner";

    public String DELETE_PROJECT_BY_ID_URL = "/delete";

    public String GET_PROJECT_BY_ID_URL = "/retrieve";

    public String GET_PROJECTS_BY_USER_ID_URL = "/retrieve/all";

    public String ADMIN_GET_ALL_PROJECTS_URL = "/admin/retrieve/all";

    public String ADMIN_DELETE_PROJECT_BY_ID_URL = "/admin/delete";

    public String ADMIN_DELETE_PROJECTS_URL = "/admin/delete/all";
}
