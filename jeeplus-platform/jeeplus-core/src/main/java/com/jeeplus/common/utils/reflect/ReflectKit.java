package com.jeeplus.common.utils.reflect;

import java.lang.reflect.Field;

public class ReflectKit {
    /**
     * 获取字段值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        int idx = fieldName.indexOf(".");
        if (idx > 0) {//实体类型字段映射
            String f1 = fieldName.substring(0, idx);
            String f2 = fieldName.substring(idx + 1);

            Object entity = ReflectKit.getFieldValue(obj, f1);
            if (ObjectKit.isNull(entity)) {
                return null;
            }
            if (f2.indexOf(".") > 0)
                return getFieldValue(entity, f2);//递归
            else
                return getFieldValue(entity, f2);
        } else {
            Object value = null;
            try {
                Field field = obj.getClass().getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    value = field.get(obj);
                }
            } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return value;
        }
    }

    /**
     * 字段名不区分大小写
     * 性能比getFieldValue低
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValueNoCaseSensitive(Object obj, String fieldName) {
        Object value = null;
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            Field f = null;
            for (Field f1 : fields) {
                if (f1.getName().toLowerCase().equals(fieldName.toLowerCase())) {
                    f = f1;
                    break;
                }
            }
            if (f != null)
                value = getFieldValue(obj, f.getName());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取字段类型
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Class getFieldType(Object obj, String fieldName) {
        Class clazz = null;
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            if (field != null) {
                field.setAccessible(true);
                clazz = field.getType();
            }
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return clazz;
    }


    /**
     * 设置字段值
     *
     * @param obj        实例对象
     * @param fieldName  对象属性，如属性中包含”.“ 表示这是一个循环嵌套属性， 会一直往下定位到最终对象的字段（支持无限级）
     * @param fieldValue 属性值
     */
    public static void setFieldValue(Object obj, String fieldName, Object fieldValue) throws IllegalAccessException {
        int idx = fieldName.indexOf(".");
        if (idx > 0) {//实体类型字段映射
            String f1 = fieldName.substring(0, idx);
            String f2 = fieldName.substring(idx + 1);

            Object entity = ReflectKit.getFieldValue(obj, f1);
            if (ObjectKit.isNull(entity)) {
                try {
                    Class clazz = ReflectKit.getFieldType(obj, f1);
                    entity = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (f2.indexOf(".") > 0)
                setFieldValue(entity, f2, fieldValue);//递归
            else
                setFieldValue2(entity, f2, fieldValue);
            setFieldValue(obj, f1, entity);
        } else {
            setFieldValue2(obj, fieldName, fieldValue);
        }
    }

    private static void setFieldValue2(Object obj, String fieldName, Object fieldValue) throws IllegalAccessException {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            if (fields != null) {
                for (Field f : fields) {
                    f.setAccessible(true);
                    if (f.getName().toLowerCase().equals(fieldName.trim().toLowerCase())) {
                        f.set(obj, fieldValue);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
