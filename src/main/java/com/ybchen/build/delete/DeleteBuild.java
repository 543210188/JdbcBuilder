package com.ybchen.build.delete;

import com.ybchen.build.BaseBuild;
import com.ybchen.build.core.TSqlPlus;
import com.ybchen.build.core.TSqlUtil;
import com.ybchen.exceptions.Assert;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName：DeleteBuild
 * @Description：构建删除语句
 * @Author：chenyb
 * @Date：2020/9/28 3:48 下午
 * @Versiion：1.0
 */
public class DeleteBuild extends BaseBuild implements TSqlUtil {
    public String buildDeleteById(Class<?> clazz, Object object) {
        return new StringBuilder().append(buildSql(clazz)).append(WHERE).append(getPrimaryKey(clazz)).append(EQUAL).append(QUOTATION_MARK).append(object).append(QUOTATION_MARK).toString();
    }

    public String buildDeleteByCondition(Class<?> clazz, TSqlPlus tSqlPlus) {
        String strWhere = tSqlPlus.getCondition();
        Assert.isTrue(StringUtils.isEmpty(strWhere),"筛选条件不能为空");
        return new StringBuilder().append(buildSql(clazz)).append(strWhere).toString();
    }
    public String buildDeleteAll(Class<?> clazz){
        return buildSql(clazz);
    }
    private String buildSql(Class<?> clazz) {
        return new StringBuilder().append(DELETE).append(FROM).append(getTableName(clazz)).toString();
    }
}
