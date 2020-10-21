package com.ybchen.build.update;

import com.ybchen.build.BaseBuild;
import com.ybchen.build.core.TSqlUtil;

/**
 * @ClassName：UpdateBuild
 * @Description：构建修改语句
 * @Author：chenyb
 * @Date：2020/9/28 3:48 下午
 * @Versiion：1.0
 */
public class UpdateBuild extends BaseBuild implements TSqlUtil {
    private Object[] objects=null;
    public <T> String buildUpdate(Class<?> clazz, T entity) {
        doResolve(clazz, entity);
        objects=arrObject;
        return new StringBuilder()
                .append(UPDATE)
                .append(getTableName(clazz))
                .append(SET)
                .append(parameter)
                .toString();
    }
    public Object[] getObjects(){
        return objects;
    }
}
