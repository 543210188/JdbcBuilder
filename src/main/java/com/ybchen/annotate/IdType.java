package com.ybchen.annotate;

/**
 * 主键生成规则
 */
public enum IdType {
    NONE(1, "用户手动赋值"), //用户赋值
    AUTO(2, "数据库自增"); //数据库自增
    private final int key;
    private final String desc;

    private IdType(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
