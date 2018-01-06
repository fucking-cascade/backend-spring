package org.latheild.schedule.api.constant;

public interface ScheduleURL {
    String GET_PROJECT_ID_URL = "/retrieve/project";

    String CHECK_SCHEDULE_EXIST_URL = "/check";

    String CREATE_SCHEDULE_URL = "/create";

    String UPDATE_SCHEDULE_URL = "/update";

    String DELETE_SCHEDULE_BY_ID_URL = "/delete";

    String GET_SCHEDULE_BY_ID_URL = "/retrieve";

    String GET_SCHEDULES_URL = "/retrieve/all";

    String ADMIN_GET_ALL_SCHEDULES_URL = "/admin/retrieve/all";

    String ADMIN_DELETE_SCHEDULE_BY_ID_URL = "/admin/delete";

    String ADMIN_DELETE_SCHEDULES_URL = "/admin/delete/all";

    String ADD_SCHEDULE_PARTICIPANT_URL = "/add/participant";

    String REMOVE_SCHEDULE_PARTICIPANT_URL = "/remove/participant";
}
