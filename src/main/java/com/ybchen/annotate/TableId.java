package com.ybchen.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableId {
    /**
     * 主键值
     * @return
     */
    String value();
    /**
     * 主键生成规则
     * @return
     */
    IdType type() default IdType.AUTO;
}
