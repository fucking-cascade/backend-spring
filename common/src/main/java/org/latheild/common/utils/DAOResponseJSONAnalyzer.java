package org.latheild.common.utils;

import org.latheild.common.domain.DAOResponse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class DAOResponseJSONAnalyzer {
    public static boolean checkError(DAOResponse daoResponse) {
        try {
            Field field = daoResponse.getClass().getDeclaredField("error");
            field.setAccessible(true);
            if (field.get(daoResponse) == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkId(String key) {
        return  (
                key.equals("UserId")
                || key.equals("TaskId")
                || key.equals("ProjectId")
                || key.equals("ProgressId")
                || key.equals("SubtaskId")
                || key.equals("ScheduleId")
                || key.equals("CommentId")
                || key.equals("FileId")
                || key.equals("OwnerId")
                || key.equals("id")
        );
    }

    @SuppressWarnings("unchecked")
    public static class Wrapper<T> {
        private T ob;

        private Class classTemplate;

        public Wrapper(T ob) {
            super();
            this.ob = ob;
            this.classTemplate = ob.getClass();
        }

        private T analyzeLinkedHashMap(LinkedHashMap linkedHashMap) {
            //System.out.println("Check LinkedHashMap---------------");
            //System.out.println(linkedHashMap.toString());
            Set set = linkedHashMap.keySet();
            try {
                ob = (T) classTemplate.newInstance();
                for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                    Object key = iterator.next();
                    Field field = ob.getClass().getDeclaredField(key.toString());
                    field.setAccessible(true);
                    if (linkedHashMap.get(key).getClass().getTypeName().equals(String.class.getTypeName())
                            || checkId(key.toString())) {
                        field.set(ob, linkedHashMap.get(key).toString());
                    } else {
                        field.set(ob, linkedHashMap.get(key));
                    }
                    //System.out.println(field.get(ob));
                    //System.out.println(key.toString() + " : " + linkedHashMap.get(key).toString() + "      - " + linkedHashMap.get(key).getClass());
                }
                return ob;
            } catch (Exception excep) {
                excep.printStackTrace();
            }
            return ob;
        }

        private ArrayList<T> analyzeArrayList(ArrayList<LinkedHashMap> linkedHashMaps) {
            //System.out.println("Check ArrayList================================");
            //System.out.println(linkedHashMaps.toString());
            ArrayList<T> arrayList = new ArrayList<>();
            for (LinkedHashMap linkedHashMap : linkedHashMaps) {
                //System.out.println("Check LinkedHashMap----------------------");
                //System.out.println(linkedHashMap.toString());
                arrayList.add(analyzeLinkedHashMap(linkedHashMap));
            }
            //System.out.println("CHeck final arraylist!=======");
            //System.out.println(arrayList.toString());
            return arrayList;
        }

        public Object analyze(DAOResponse daoResponse) {
            if (checkError(daoResponse)) {
                return null;
            }
            if (daoResponse.getData().getClass().getTypeName().equals(ArrayList.class.getTypeName())) {
                return analyzeArrayList((ArrayList) daoResponse.getData());
            } else {
                return analyzeLinkedHashMap((LinkedHashMap) daoResponse.getData());
            }
        }
    }

}
