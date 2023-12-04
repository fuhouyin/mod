package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityUtil {

    /**
     * 实体类转Map<String,Object>
     */
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

    /**
     * 获取实体类中所有不为空的字段名
     */
    public static List<String> findNotNullFields(Object obj) {
        List<String> notNullFields = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value!= null) {
                    notNullFields.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return notNullFields;
    }

    /**
     * 根据字段名获取值
     */
    public static Object getFieldValue(String fieldName, Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * 解析json字符串为MultiValueMap
     */
    public static MultiValueMap<String, Object> getMultiValueMap(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(json, Map.class);
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            multiValueMap.add(entry.getKey(), entry.getValue());
        }
        return multiValueMap;
    }
}
