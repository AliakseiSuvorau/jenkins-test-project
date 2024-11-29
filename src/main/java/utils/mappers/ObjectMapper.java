package utils.mappers;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class ObjectMapper {
    public final Map<String, Object> toMap(final Object object) {
        Map<String, Object> map = new LinkedHashMap<>();

        var fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            var field = fields[i];
            var fieldName = field.getName();

            if (field.accessFlags().contains(AccessFlag.PUBLIC)) {
                try {
                    map.put(fieldName, field.get(object));
                } catch (IllegalAccessException ignored) {
                    // Will never happen
                }
                continue;
            }
            String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

            Method method;
            Object fieldValue = null;
            try {
                method = object.getClass().getMethod(getterName);
                fieldValue = method.invoke(object);
            } catch (NoSuchMethodException e) {
                System.err.println("Private field \"" + field.getName() + "\" does not have a public getter. Ignoring...");
                continue;
            } catch (IllegalAccessException ignored) {
                // This case is dealt with above
            } catch (InvocationTargetException ignored) {
                // Will never happen
            }

            map.put(fieldName, fieldValue);
        }

        return map;
    }
}
