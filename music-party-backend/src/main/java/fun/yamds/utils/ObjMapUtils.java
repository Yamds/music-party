package fun.yamds.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjMapUtils {

    /**
     * 将对象转换为字段名-值的Map
     * @param bean 要转换的JavaBean对象
     * @return 包含字段名和值的Map（过滤掉静态字段）
     */
    // Map<String, Object> dataMap = BeanMapUtils.convertToMap(response.getData());
    public static Map<String, Object> convertToMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean == null) return map;

        Class<?> clazz = bean.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                // 跳过静态字段
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) continue;

                try {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(bean));
                } catch (IllegalAccessException e) {
                    // 根据需求处理异常，这里静默跳过无法访问的字段
                }
            }
            clazz = clazz.getSuperclass(); // 处理继承字段
        }
        return map;
    }

    /**
     * 带过滤功能的转换方法
     * @param bean 要转换的对象
     * @param excludeFields 需要排除的字段名（可变参数）
     */
    // Map<String, Object> filteredMap = BeanMapUtils.convertToMap(user, "password", "token");
    public static Map<String, Object> convertToMap(Object bean, String... excludeFields) {
        Map<String, Object> map = convertToMap(bean);
        for (String field : excludeFields) {
            map.remove(field);
        }
        return map;
    }
}
