package com.ybchen.exceptions;

/**
 * @ClassName：Assert
 * @Description：异常
 * @Author：chenyb
 * @Date：2020/9/28 3:58 下午
 * @Versiion：1.0
 */
public class Assert {
    public static void isTrue(Boolean flag, String msg) {
        if (flag) {
            throw new IllegalArgumentException(msg);
        }
    }
}
