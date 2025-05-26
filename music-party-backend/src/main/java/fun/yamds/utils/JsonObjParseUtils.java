package fun.yamds.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.lang.reflect.*;
import java.util.*;

// DeepSeek
// 利用反射拿到对象内部的字段
// 再将字符串通过拿到的字段，转换成你想要的对象
// 字符串中多出的内容会被忽略 少的也忽略

public class AutoMapper {

    /**
     * 自定义泛型类型引用类（类似Spring的ParameterizedTypeReference）
     */
    public abstract static class ParameterizedTypeReference<T> {
        private final Type type;

        protected ParameterizedTypeReference() {
            Type superClass = getClass().getGenericSuperclass();
            this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        }

        public Type getType() {
            return type;
        }
    }

    /**
     * 主入口方法
     */
    public static <T> T parse(String jsonStr, ParameterizedTypeReference<T> typeRef) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return mapJsonToObject(jsonObject, typeRef.getType());
    }

    /**
     * 递归映射JSON对象到Java对象
     */
    private static <T> T mapJsonToObject(JSONObject json, Type targetType) {
        try {
            Class<?> rawType = getRawType(targetType);
            T instance = (T) rawType.getDeclaredConstructor().newInstance();
            Map<TypeVariable<?>, Type> typeArgs = extractTypeArguments(targetType, rawType);

            for (Field field : getAllFields(rawType)) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (json.containsKey(fieldName)) {
                    Type resolvedType = resolveFieldType(field.getGenericType(), typeArgs);
                    Object value = convertValue(json.get(fieldName), resolvedType);
                    field.set(instance, value);
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Auto mapping failed", e);
        }
    }

    /**
     * 类型转换核心方法
     */
    private static Object convertValue(Object value, Type targetType) {
        if (value == null) return null;

        // 处理集合类型（List/Set等）
        if (targetType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) targetType;
            Type rawType = pType.getRawType();

            if (List.class.isAssignableFrom((Class<?>) rawType)) {
                return parseCollection((JSONArray) value, pType);
            }
            if (Set.class.isAssignableFrom((Class<?>) rawType)) {
                return parseCollection((JSONArray) value, pType);
            }
        }

        // 处理数组
        if (targetType instanceof Class && ((Class<?>) targetType).isArray()) {
            return parseArray((JSONArray) value, ((Class<?>) targetType).getComponentType());
        }

        // 处理嵌套对象
        if (value instanceof JSONObject) {
            return mapJsonToObject((JSONObject) value, targetType);
        }

        // 处理基本类型和包装类
        if (targetType instanceof Class) {
            Class<?> clazz = (Class<?>) targetType;
            if (clazz == String.class) return value.toString();
            if (clazz.isPrimitive() || isWrapperType(clazz)) return value;
        }

        // 处理其他未明确处理的类型
        return value;
    }

    /**
     * 处理泛型集合
     */
    private static Collection<?> parseCollection(JSONArray jsonArray, ParameterizedType pType) {
        try {
            Type elementType = pType.getActualTypeArguments()[0];
            Collection<Object> collection = createCollectionInstance((Class<?>) pType.getRawType());
            for (Object item : jsonArray) {
                collection.add(convertValue(item, elementType));
            }
            return collection;
        } catch (Exception e) {
            throw new RuntimeException("Collection parsing failed", e);
        }
    }

    /**
     * 处理数组类型
     */
    private static Object parseArray(JSONArray jsonArray, Class<?> componentType) {
        Object array = Array.newInstance(componentType, jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            Array.set(array, i, convertValue(jsonArray.get(i), componentType));
        }
        return array;
    }

    /**
     * 类型解析工具方法
     */
    private static Class<?> getRawType(Type type) {
        if (type instanceof Class) return (Class<?>) type;
        if (type instanceof ParameterizedType) return (Class<?>) ((ParameterizedType) type).getRawType();
        throw new IllegalArgumentException("Unsupported type: " + type);
    }

    private static Map<TypeVariable<?>, Type> extractTypeArguments(Type type, Class<?> rawType) {
        Map<TypeVariable<?>, Type> map = new HashMap<>();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            TypeVariable<?>[] typeParams = rawType.getTypeParameters();
            Type[] actualArgs = pType.getActualTypeArguments();
            for (int i = 0; i < typeParams.length; i++) {
                map.put(typeParams[i], actualArgs[i]);
            }
        }
        return map;
    }

    private static Type resolveFieldType(Type fieldType, Map<TypeVariable<?>, Type> context) {
        if (fieldType instanceof TypeVariable) {
            return context.getOrDefault(fieldType, Object.class);
        }
        if (fieldType instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) fieldType;
            Type[] actualTypes = pType.getActualTypeArguments();
            Type[] resolvedTypes = new Type[actualTypes.length];
            for (int i = 0; i < actualTypes.length; i++) {
                resolvedTypes[i] = resolveFieldType(actualTypes[i], context);
            }
            return new ParameterizedTypeImpl(
                    (Class<?>) pType.getRawType(),
                    resolvedTypes,
                    pType.getOwnerType()
            );
        }
        return fieldType;
    }

    /**
     * 集合实例创建方法
     */
    private static Collection<Object> createCollectionInstance(Class<?> collectionType) {
        if (collectionType.isInterface()) {
            if (List.class.isAssignableFrom(collectionType)) return new ArrayList<>();
            if (Set.class.isAssignableFrom(collectionType)) return new HashSet<>();
            throw new IllegalArgumentException("Unsupported collection interface: " + collectionType);
        }
        try {
            return (Collection<Object>) collectionType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Collection creation failed", e);
        }
    }

    /**
     * 获取所有字段（包括父类）
     */
    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 判断是否为包装类型
     */
    private static boolean isWrapperType(Class<?> clazz) {
        return clazz == Integer.class || clazz == Long.class || clazz == Double.class
                || clazz == Boolean.class || clazz == Character.class
                || clazz == Byte.class || clazz == Short.class
                || clazz == Float.class || clazz == Void.class;
    }

    /**
     * 自定义ParameterizedType实现
     */
    static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class<?> rawType;
        private final Type[] actualTypeArguments;
        private final Type ownerType;

        ParameterizedTypeImpl(Class<?> rawType, Type[] actualTypeArguments, Type ownerType) {
            this.rawType = rawType;
            this.actualTypeArguments = actualTypeArguments;
            this.ownerType = ownerType;
        }

        @Override public Type[] getActualTypeArguments() { return actualTypeArguments; }
        @Override public Type getRawType() { return rawType; }
        @Override public Type getOwnerType() { return ownerType; }
    }
}