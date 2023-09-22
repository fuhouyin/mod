package utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityUtil {

    public static Map<String, Object> entityToMap(Object entity) {
        Map<String, Object> result = new HashMap<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                result.put(field.getName(), field.get(entity));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
