package com.ybchen.annotate;

/**
 * 表字段类型
 */
public enum FieldType {
    VARCHAR(1,"字符串"),
    NUMERIC(2,"数字"),
    DATE(3,"日期");
    private final int key;
    private final String desc;
    private FieldType(int key,String desc) {
        this.key = key;
        this.desc=desc;
    }

    public int getKey() {
        return this.key;
    }

    public String getDesc() {
        return this.desc;
    }
}
