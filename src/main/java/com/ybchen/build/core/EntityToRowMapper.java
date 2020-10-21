package com.ybchen.build.core;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.ybchen.annotate.TableField;
import org.springframework.jdbc.core.RowMapper;

/**
 * @ClassName：EntityToRowMapper
 * @Description：行转实体
 * @Author：chenyb
 * @Date：2020/9/28 4:37 下午
 * @Versiion：1.0
 */
public class EntityToRowMapper<T> implements RowMapper<T> {
    /**
     * 添加字段注释.
     */
    private Class<?> targetClazz;

    /**
     * 添加字段注释.
     */
    private HashMap<String, Field> fieldMap;

    /**
     * 构造函数.
     *
     * @param targetClazz .
     */
    public EntityToRowMapper(Class<?> targetClazz) {
        this.targetClazz = targetClazz;
        fieldMap = new HashMap<>();
        TableField annotation=null;
        Field[] fields = targetClazz.getDeclaredFields();
        for (Field field : fields) {
            annotation = field.getAnnotation(TableField.class);
            if (annotation!=null){
                fieldMap.put(annotation.value(), field);
            }
        }
    }

    @Override
    public T mapRow(ResultSet resultSet, int arg1) throws SQLException {
        T obj = null;
        try {
            obj = (T) targetClazz.newInstance();
            final ResultSetMetaData metaData = resultSet.getMetaData();
            int columnLength = metaData.getColumnCount();
            String columnName = null;
            for (int i = 1; i <= columnLength; i++) {
                columnName = metaData.getColumnName(i);
                Class fieldClazz = fieldMap.get(columnName).getType();
                Field field = fieldMap.get(columnName);
                field.setAccessible(true);
                if (fieldClazz == int.class || fieldClazz == Integer.class) {
                    field.set(obj, resultSet.getInt(columnName));
                } else if (fieldClazz == boolean.class || fieldClazz == Boolean.class) {
                    field.set(obj, resultSet.getBoolean(columnName));
                } else if (fieldClazz == String.class) {
                    field.set(obj, resultSet.getString(columnName));
                } else if (fieldClazz == float.class) {
                    field.set(obj, resultSet.getFloat(columnName));
                } else if (fieldClazz == double.class || fieldClazz == Double.class) {
                    field.set(obj, resultSet.getDouble(columnName));
                } else if (fieldClazz == BigDecimal.class) {
                    field.set(obj, resultSet.getBigDecimal(columnName));
                } else if (fieldClazz == short.class || fieldClazz == Short.class) {
                    field.set(obj, resultSet.getShort(columnName));
                } else if (fieldClazz == Date.class) {
                    field.set(obj, resultSet.getDate(columnName));
                } else if (fieldClazz == Timestamp.class) {
                    field.set(obj, resultSet.getTimestamp(columnName));
                } else if (fieldClazz == Long.class || fieldClazz == long.class) {
                    field.set(obj, resultSet.getLong(columnName));
                }
                field.setAccessible(false);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return obj;
    }
}
