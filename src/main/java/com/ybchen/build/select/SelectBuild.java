package com.ybchen.build.select;

import com.ybchen.build.BaseBuild;
import com.ybchen.build.core.TSqlPlus;

/**
 * @ClassName：Select
 * @Description：构建查询语句
 * @Author：chenyb
 * @Date：2020/9/28 3:32 下午
 * @Versiion：1.0
 */
public class SelectBuild extends BaseBuild {
    /**
     * 查询一条记录
     *
     * @param clazz
     * @param tSqlPlus
     * @return
     */
    public String buildSelectOne(Class<?> clazz, TSqlPlus tSqlPlus) {
        return buildSelectList(clazz, tSqlPlus) + LIMIT_ONE;
    }

    /**
     * 查询集合
     *
     * @param clazz
     * @param tSqlPlus
     * @return
     */
    public String buildSelectList(Class<?> clazz, TSqlPlus tSqlPlus) {
        return new StringBuilder().append(buildSql(clazz,getTableField(clazz))).append(tSqlPlus.getCondition()).toString();
    }

    /**
     * 分页查询
     *
     * @param clazz
     * @param tSqlPlus
     * @param start
     * @param end
     * @return
     */
    public String buildSelectPage(Class<?> clazz, TSqlPlus tSqlPlus, int start, int end) {
        if (start < 1) {
            start = 1;
        }
        return new StringBuilder().append(buildSelectList(clazz, tSqlPlus)).append(LIMIT).append((start - 1) * end).append(COMMA).append(end).toString();
    }

    /**
     * 获取总行数
     *
     * @param clazz
     * @param tSqlPlus
     * @return
     */
    public String buildSelectCount(Class<?> clazz, TSqlPlus tSqlPlus) {
        return new StringBuilder().append(buildSql(clazz,COUNT)).append(tSqlPlus.getCondition()).toString();
    }
    private String buildSql(Class<?> clazz,String fields){
        return new StringBuilder().append(SELECT).append(fields).append(FROM).append(getTableName(clazz)).toString();
    }
}
