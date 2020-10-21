package com.ybchen.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mysql连接信息
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
public @interface MySqlConnection {
    /**
     * 连接字符串
     * @return
     */
    String url();

    /**
     * 用户名
     * @return
     */
    String username();

    /**
     * 密码
     * @return
     */
    String passwd();
}
