package com.ybchen.test.domain;

import com.ybchen.annotate.*;
import javafx.scene.chart.ValueAxis;

import java.util.Date;

/**
 * @ClassName：Table1
 * @Description：TODO
 * @Author：chenyb
 * @Date：2020/9/28 4:53 下午
 * @Versiion：1.0
 */
@TableName("s_user")
public class User {
    @TableField("id")
    @TableId(value = "id")
    private String id;
    @TableField(value = "user_name", type = FieldType.VARCHAR)
    private String userName;
    @TableField(value = "age", type = FieldType.NUMERIC)
    private int age;
    @TableField(value = "create_date")
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Table1{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", createDate=" + createDate +
                '}';
    }
}
