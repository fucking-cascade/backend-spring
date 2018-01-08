package org.latheild.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DAORequestJSONWrapper {
    private static final String STRING_TYPE = String.class.getTypeName();

    private static final String INT_TYPE = Integer.class.getTypeName();

    public static final String FIND_ONE = "one";

    public static final String FIND_ALL = "all";

    public static String checkException(String key) {
        if (key.equals("userId")) {
            key = "UserId";
        } else if (key.equals("taskId")) {
            key = "TaskId";
        } else if (key.equals("projectId")) {
            key = "ProjectId";
        } else if (key.equals("progressId")) {
            key = "ProgressId";
        } else if (key.equals("subtaskId")) {
            key = "SubtaskId";
        } else if (key.equals("scheduleId")) {
            key = "ScheduleId";
        } else if (key.equals("commentId")) {
            key = "CommentId";
        } else if (key.equals("fileId")) {
            key = "FileId";
        } else if (key.equals("ownerId")) {
            key = "OwnerId";
        }
        return key;
    }

    public static String generateJSON(HashMap<String, Object> kvPairs) {
        StringBuilder builder = new StringBuilder("{");
        Set set = kvPairs.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            Object value = kvPairs.get(key);
            key = checkException(key);
            builder.append("\"").append(key).append("\" : ");
            if (value.getClass().getTypeName().equals(STRING_TYPE)) {
                builder.append("\"").append(value).append("\"");
            } else if (value.getClass().getTypeName().equals(INT_TYPE)) {
                builder.append(value);
            } else {
                builder.append(value);
            }
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append("}");

        return builder.toString();
    }

    private static HashMap<String, Object> generateKVPairs(ArrayList<String> fieldNames, Object object) {
        HashMap<String, Object> kvPairs = new HashMap<>();
        try {
            for (String fieldName : fieldNames) {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                kvPairs.put(fieldName, field.get(object));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kvPairs;
    }

    private static HashMap<String, Object> generateKVPair(String fieldName, Object value) {
        HashMap<String, Object> kvPair = new HashMap<>();
        kvPair.put(fieldName, value);
        return kvPair;
    }

    public static String setFindRequestJSON(ArrayList<String> fieldNames, Object object, String type) {
        return "{\"cond\" : " + generateJSON(generateKVPairs(fieldNames, object)) + ", \"type\" : \"" + type + "\"}";
    }

    public static String setFindRequestJSON(String fieldName, Object value, String type) {
        return "{\"cond\" : " + generateJSON(generateKVPair(fieldName, value)) + ", \"type\" : \"" + type + "\"}";
    }

    public static String setCountRequestJSON(ArrayList<String> fieldNames, Object object) {
        return generateJSON(generateKVPairs(fieldNames, object));
    }

    public static String setCountRequestJSON(String fieldName, Object value) {
        return generateJSON(generateKVPair(fieldName, value));
    }

    public static String setDeleteRequestJSON(ArrayList<String> fieldNames, Object object) {
        return generateJSON(generateKVPairs(fieldNames, object));
    }

    public static String setDeleteRequestJSON(String fieldName, Object value) {
        return generateJSON(generateKVPair(fieldName, value));
    }

    public static String setCreateRequestJSON(ArrayList<String> fieldNames, Object object) {
        return generateJSON(generateKVPairs(fieldNames, object));
    }

    public static String setCreateRequestJSON(String fieldName, Object value) {
        return generateJSON(generateKVPair(fieldName, value));
    }

    public static String setUpdateRequestJSON(ArrayList<String> fieldNames, Object object) {
        return generateJSON(generateKVPairs(fieldNames, object));
    }

    public static String setUpdateRequestJSON(String fieldName, Object value) {
        return generateJSON(generateKVPair(fieldName, value));
    }
}
