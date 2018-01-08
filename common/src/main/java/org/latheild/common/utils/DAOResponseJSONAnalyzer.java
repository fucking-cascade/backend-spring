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

    @SuppressWarnings("unchecked")
    public static class Wrapper<T> {
        private T ob;

        public Wrapper(T ob) {
            super();
            this.ob = ob;
        }

        private T analyzeLinkedHashMap(LinkedHashMap linkedHashMap) {
            Set set = linkedHashMap.keySet();
            try {
                for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                    Object key = iterator.next();
                    Field field = ob.getClass().getDeclaredField(key.toString());
                    field.setAccessible(true);
                    field.set(ob, linkedHashMap.get(key).toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ob;
        }

        private ArrayList<T> analyzeArrayList(ArrayList<LinkedHashMap> linkedHashMaps) {
            ArrayList<T> arrayList = new ArrayList<>();
            for (LinkedHashMap linkedHashMap : linkedHashMaps) {
                arrayList.add(analyzeLinkedHashMap(linkedHashMap));
            }
            return arrayList;
        }

        public Object analyze(DAOResponse daoResponse) {
            if (checkError(daoResponse)) {
                return null;
            }
            if (daoResponse.getData().getClass().getTypeName().equals(ArrayList.class.getTypeName())) {
                /*Test<ArrayList<T>> returnedObject = new Test<>(new ArrayList<T>());
                returnedObject.set(analyzeArrayList((ArrayList) object));
                return returnedObject;*/
                return analyzeArrayList((ArrayList) daoResponse.getData());
            } else {
                return analyzeLinkedHashMap((LinkedHashMap) daoResponse.getData());
            }
        }

        /*public static class Test<S> {
            private S test;

            private Test(S test) {
                super();
                this.test = test;
            }

            private void set(S test) {
                this.test = test;
            }

            public S get() {
                return this.test;
            }
        }*/
    }

}
