package com.ybchen.build;

import com.ybchen.annotate.IdType;
import com.ybchen.annotate.TableField;
import com.ybchen.annotate.TableId;
import com.ybchen.annotate.TableName;
import com.ybchen.build.core.TSqlUtil;
import com.ybchen.exceptions.Assert;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName：BaseBuild
 * @Description：基础build工具类
 * @Author：chenyb
 * @Date：2020/9/28 5:13 下午
 * @Versiion：1.0
 */
public class BaseBuild implements TSqlUtil {
    protected String insertFields;
    protected String updateFields;
    protected String parameter;
    protected Object[] arrObject;

    /**
     * 获取注解上表名
     *
     * @param clazz
     * @return
     */
    protected final String getTableName(Class<?> clazz) {
        Assert.isTrue(clazz.isAnnotationPresent(TableName.class) == false, "未添加表名注解!");
        String tableName = ((TableName) clazz.getAnnotation(TableName.class)).value();
        Assert.isTrue(tableName == "" || "".equals(tableName), "表名不能为空!");
        return tableName;
    }

    /**
     * 获取注解上的表列名
     *
     * @param clazz
     * @return
     */
    protected final String getTableField(Class<?> clazz) {
        StringBuilder stringBuilder = new StringBuilder();
        Field f;
        TableField tableField;
        Field[] fields = clazz.getDeclaredFields();
        int max = fields.length;
        for (int i = 0; i < max; i++) {
            f = fields[i];
            tableField = f.getAnnotation(TableField.class);
            if (tableField != null) {
                stringBuilder.append(tableField.value()).append(",");
            }
        }
        Assert.isTrue(stringBuilder.toString().length() == 0, "列表未指定");
        return stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf(','));
    }

    /**
     * 获取主键字段
     *
     * @param clazz
     * @return
     */
    protected final String getPrimaryKey(Class<?> clazz) {
        String primaryKey = null;
        Field f;
        TableId pKey;
        Field[] fields = clazz.getDeclaredFields();
        int max = fields.length;
        for (int i = 0; i < max; i++) {
            f = fields[i];
            pKey = f.getAnnotation(TableId.class);
            if (pKey != null) {
                primaryKey = pKey.value();
            }
        }
        Assert.isTrue(primaryKey == null, "主键未指定");
        return primaryKey;
    }

    protected final <T> void init(Class<?> clazz, T entity) {
        Object obj = null, resultObj;
        StringBuilder stringBuilderFields = new StringBuilder();
        StringBuilder stringBuilderParameter = new StringBuilder();
        TableId pKey;
        TableField tf;
        Field f;
        Field[] arrFields = entity.getClass().getDeclaredFields();
        int max = arrFields.length;
        List<Object> objectList = new ArrayList<>(max);
        for (int x = 0; x < max; x++) {
            if (x > 0) {
                stringBuilderFields.append(",");
                stringBuilderParameter.append(",");
            }
            f = arrFields[x];
            obj = null;
            f.setAccessible(true);
            pKey = f.getAnnotation(TableId.class);
            tf = f.getAnnotation(TableField.class);
            if (pKey != null) {
                stringBuilderFields.append(pKey.value());
                obj = pKey.value();
                stringBuilderParameter.append(pKey.type() == IdType.AUTO ? NULL : QUESTION_MARK);
                if (pKey.type() == IdType.AUTO) {
                    continue;
                }
            } else if (tf != null && obj == null) {
                if (tf.exist() == false && stringBuilderFields.toString().length() > 0 && stringBuilderParameter.toString().length() > 0) {
                    stringBuilderFields.deleteCharAt(stringBuilderFields.length() - 1);
                    stringBuilderParameter.deleteCharAt(stringBuilderParameter.length() - 1);
                    continue;
                }
                stringBuilderFields.append(tf.value());
                stringBuilderParameter.append(QUESTION_MARK);
            } else {
                stringBuilderFields.append(f.getName());
                stringBuilderParameter.append(QUESTION_MARK);
            }
            try {
                resultObj = f.get(entity);
                if (f.getType() == Date.class) {
                    String format = tf.format();
                    format = format == null || "".equals(format) ? "yyyy-MM-dd" : format;
                    SimpleDateFormat formatter = new SimpleDateFormat(format);
                    objectList.add(resultObj == null ? null : formatter.format(resultObj));
                } else {
                    objectList.add(resultObj == null ? null : resultObj.toString().trim());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                objectList.add(null);
            }
            f.setAccessible(false);
        }
        insertFields = stringBuilderFields.toString();
        parameter = stringBuilderParameter.toString();
        arrObject = objectList.toArray();
    }

    protected final <T> void doResolve(Class<?> clazz, T entity) {
        Object resultObj, primaryKey = null, primaryKeyValue = null;
        StringBuilder stringBuilderParameter = new StringBuilder();
        TableId pKey;
        TableField tf = null;
        Field f;
        Field[] arrFields = entity.getClass().getDeclaredFields();
        int max = arrFields.length;
        List<Object> objectList = new ArrayList<>(max);
        for (int x = 0; x < max; x++) {
            f = arrFields[x];
            f.setAccessible(true);
            pKey = f.getAnnotation(TableId.class);
            tf = f.getAnnotation(TableField.class);
            try {
                resultObj = f.get(entity);
                if (resultObj == null&&f.getType() != Date.class) {
                    continue;
                }
                if (pKey != null) {
                    primaryKey = pKey.value();
                    primaryKeyValue = resultObj;
                    continue;
                }
                if (tf != null) {
                    if (tf.exist() == false && stringBuilderParameter.toString().length() > 0) {
                        continue;
                    }
                    stringBuilderParameter.append(tf.value() + SET_VALUE_TEMPLATE);
                } else {
                    stringBuilderParameter.append(f.getName() + SET_VALUE_TEMPLATE);
                }
                if (f.getType() == Date.class) {
                    if (resultObj==null){
                        objectList.add(null);
                    }else {
                        String format = tf.format();
                        format = format == null || "".equals(format) ? "yyyy-MM-dd" : format;
                        SimpleDateFormat formatter = new SimpleDateFormat(format);
                        objectList.add(formatter.format(resultObj));
                    }
                } else {
                    objectList.add(resultObj == "" ? null : resultObj.toString().trim());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException("参数" + f.getName() + "获取失败！");
            } finally {
                f.setAccessible(false);
            }
        }
        if (stringBuilderParameter.toString().length()==0) {
            throw new RuntimeException("无修改数据！");
        }
        if (primaryKey==null||"".equals(primaryKey)||primaryKeyValue==null||"".equals(primaryKeyValue)) {
            throw new RuntimeException("主键未设置或未赋值！");
        } else {
            stringBuilderParameter.deleteCharAt(stringBuilderParameter.length() - 1);
            stringBuilderParameter.append(WHERE).append(primaryKey).append(EQUAL).append(QUESTION_MARK);
            objectList.add(primaryKeyValue);
        }
        parameter = stringBuilderParameter.append(";").toString();
        arrObject = objectList.toArray();
    }
}
