package com.example.accounts.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UpdateNonNullFieldsUtil {
    // method use to update old data with new one
    // if one of the field in the new data is null, skip that field
    // target: old data that need to update
    // source: new input data
    public static <T> void updateNonNullFields(T target, T source) {
        if (target == null || source == null) {
            return;  // If target or source is null, no update is possible
        }
        Field[] sourceFields = source.getClass().getDeclaredFields();
        AccessibleObject.setAccessible(sourceFields, true);
        Field[] targetFields = target.getClass().getDeclaredFields();
        AccessibleObject.setAccessible(targetFields, true);
        Map<String, Field> map = new HashMap<>();
        for (Field field : targetFields) {
            map.put(field.getName(), field);
        }
        for (Field field : sourceFields) {
            try {
                if (field.get(source) != null ) {
                    Field f = map.get(field.getName());
                    f.set(target, field.get(source));
                }
            }catch (NullPointerException ex){
                System.out.println(field.getName());
                System.out.println(field.getType());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
