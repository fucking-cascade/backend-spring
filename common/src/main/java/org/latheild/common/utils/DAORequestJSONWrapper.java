package org.latheild.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DAORequestJSONWrapper {
    private static final String STRING_TYPE = String.class.getTypeName();

    public static final String FIND_ONE = "one";

    public static final String FIND_ALL = "all";

    private static String generateJSON(HashMap<String, Object> kvPairs) {
        //System.out.println("Check KVPAIRS!_______________");
        //System.out.println(kvPairs.toString());
        StringBuilder builder = new StringBuilder("{");
        Set set = kvPairs.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            Object value = kvPairs.get(key);
            builder.append("\"").append(key).append("\" : ");
            if (value.getClass().getTypeName().equals(STRING_TYPE)
                    && !DAOResponseJSONAnalyzer.checkId(key)) {
                builder.append("\"").append(value).append("\"");
            } else {
                builder.append(value);
            }
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        builder.append("}");
        //System.out.println("CHeck builder---------");
        //System.out.println(builder.toString());

        return builder.toString();
    }

    private static HashMap<String, Object> generateKVPairs(ArrayList<String> fieldNames, Object object) {
        HashMap<String, Object> kvPairs = new HashMap<>();
        //System.out.println("DEBUG REQUEST JSON BUILDER=========================");
        //System.out.println(fieldNames.toString());
        //System.out.println(object.toString());
        try {
            for (String fieldName : fieldNames) {
                Field field = object.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                kvPairs.put(fieldName, field.get(object));
                //System.out.println("debug field names===-------------==");
                //System.out.println(fieldName + " : " + field.get(object).toString() + "   - " + field.get(object).getClass());
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
