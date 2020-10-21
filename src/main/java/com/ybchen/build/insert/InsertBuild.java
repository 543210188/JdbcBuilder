package com.ybchen.build.insert;

import com.ybchen.build.BaseBuild;
import com.ybchen.build.core.TSqlUtil;

/**
 * @ClassName：InsertBuild
 * @Description：构建新增语句
 * @Author：chenyb
 * @Date：2020/10/13 3:10 下午
 * @Versiion：1.0
 */
public class InsertBuild extends BaseBuild {
    private Object[] objects=null;
    public <T> String buildInsert(Class<?> clazz, T entity) {
        init(clazz, entity);
        objects=arrObject;
        return new StringBuilder()
                .append(INSERT)
                .append(IN_TO)
                .append(getTableName(clazz))
                .append(LEFT_BRACKET)
                .append(insertFields)
                .append(RIGHT_BRACKET)
                .append(VALUES)
                .append(LEFT_BRACKET)
                .append(parameter)
                .append(RIGHT_BRACKET)
                .append(SEMICOLON)
                .toString();
    }
    public Object[] getObjects(){
        return objects;
    }
}
