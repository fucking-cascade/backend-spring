package org.latheild.comment.api.constant;

public interface CommentURL {
    String ADD_COMMENT_URL = "/add";

    String DELETE_COMMENT_BY_ID_URL = "/delete";

    String GET_COMMENT_BY_ID_URL = "/retrieve";

    String GET_COMMENTS_URL = "/retrieve/all";

    String ADMIN_GET_ALL_COMMENTS_URL = "/admin/retrieve/all";

    String ADMIN_DELETE_COMMENT_BY_ID_URL = "/admin/delete";

    String ADMIN_DELETE_ALL_COMMENTS_URL = "/admin/delete/all";
}
