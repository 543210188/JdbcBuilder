package com.ybchen.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableField {
    /**
     * 列字段
     *
     * @return
     */
    String value();

    /**
     * 字段类型，默认字符串，有字符串、数字、日期
     *
     * @return
     */
    FieldType type() default FieldType.VARCHAR;

    /**
     * 格式化供日期字段使用
     * @return
     */
    String format() default "";

    /**
     * 是否支持模糊搜索，默认全字段匹配
     *
     * @return
     */
    boolean isLike() default false;

    /**
     * 数据库表字段是否存在
     *
     * @return
     */
    boolean exist() default true;

    /**
     * 数字类型，保留的位数，非四舍五入
     *
     * @return
     */
    int numericScale() default 0;
}
