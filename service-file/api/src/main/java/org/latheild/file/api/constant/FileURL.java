package org.latheild.file.api.constant;

public interface FileURL {
    String CHECK_FILE_EXIST_URL = "/check";

    String UPLOAD_FILE_URL = "/upload";

    String RENAME_FILE_URL = "/update";

    String DELETE_FILE_BY_ID_URL = "/delete";

    String GET_FILE_BY_ID_URL = "/retrieve";

    String GET_FILES_URL = "/retrieve/all";

    String ADMIN_GET_ALL_FILES_URL = "/admin/retrieve/all";

    String ADMIN_DELETE_FILE_BY_ID_URL = "/admin/delete";

    String ADMIN_DELETE_FILES_URL = "/admin/delete/all";
}
