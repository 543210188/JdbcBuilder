package com.ybchen.build.core;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @ClassName：TSqlPlus
 * @Description：组装Where
 * @Author：chenyb
 * @Date：2020/9/29 10:41 上午
 * @Versiion：1.0
 */
public class TSqlPlus extends AbstractJdbcBuilderTSql<TSqlPlus> implements TSqlUtil{

    @Override
    public TSqlPlus getSelf() {
        return this;
    }

    public TSqlPlus AND(String column, Object value) {
        handAnd(column, value);
        return this;
    }

    public TSqlPlus FULL_LIKE(String column, Object value) {
        handLike(1, column, value, PERCENT, PERCENT);
        return this;
    }

    public TSqlPlus LEFT_LIKE(String column, Object value) {
        handLike(2, column, value, PERCENT, null);
        return this;
    }

    public TSqlPlus RIGHT_LIKE(String column, Object value) {
        handLike(3, column, value, null, PERCENT);
        return this;
    }

    public TSqlPlus IS_NULL(String column) {
        handIsNull(column);
        return this;
    }

    public TSqlPlus IS_NOT_NULL(String column) {
        handIsNotNull(column);
        return this;
    }

    public TSqlPlus IN(String column, Collection<?> collection) {
        handInOrNotIn(column, collection, true);
        return this;
    }

    public TSqlPlus IN(String column, String value) {
        handInOrNotIn(column, value, true);
        return this;
    }

    public TSqlPlus NOT_IN(String column, Collection<?> collection) {
        handInOrNotIn(column, collection, false);
        return this;
    }

    public TSqlPlus NOT_IN(String column, String value) {
        handInOrNotIn(column, value, false);
        return this;
    }

    public TSqlPlus BETWEEN(String column, String value1, String value2) {
        handBetween(column, value1, value2);
        return this;
    }

    public TSqlPlus BETWEEN(String column, int value1, int value2) {
        handBetween(column, value1, value2);
        return this;
    }

    public TSqlPlus BETWEEN(String column, Date value1, Date value2) {
        handBetween(column, value1, value2);
        return this;
    }

    public TSqlPlus ORDEER_BY(String column){
        handOrderBy(column);
        return this;
    }

    private void handAnd(String column, Object value) {
        WHERE(new StringBuilder().append(column).append(EQUAL).append(value instanceof Integer ? value : QUOTATION + value + QUOTATION).toString());
    }

    private void handLike(int index, String column, Object value, String leftLike, String rightLike) {
        if (value == null || EMPTY.equals(value)) {
            return;
        }
        value = value instanceof Integer ? value : QUOTATION_MARK + value + QUOTATION_MARK;
        switch (index) {
            case 1:
                WHERE(MessageFormat.format(SQL_FULL_LIKE, column, QUOTATION_MARK + leftLike + QUOTATION_MARK, value, QUOTATION_MARK + rightLike + QUOTATION_MARK));
                break;
            case 2:
                WHERE(MessageFormat.format(SQL_LEFT_OR_RIGHT_LIKE, column, QUOTATION_MARK + leftLike + QUOTATION_MARK, value));
                break;
            case 3:
                WHERE(MessageFormat.format(SQL_LEFT_OR_RIGHT_LIKE, column, value, QUOTATION_MARK + rightLike + QUOTATION_MARK));
                break;
        }
    }

    private void handIsNull(String column) {
        WHERE(column + IS_NULL);
    }

    private void handIsNotNull(String column) {
        WHERE(column + IS_NOT_NULL);
    }

    private void handInOrNotIn(String column, Collection<?> collection, boolean isIn) {
        if (collection == null || collection.size() == 0) {
            return;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        collection.forEach(e -> stringBuilder.append(e).append(COMMA));
        String data=stringBuilder.toString();
        WHERE(column + MessageFormat.format(isIn ? IN : NOT_IN, data.substring(0,data.length()-1)));
    }

    private void handInOrNotIn(String column, String value, boolean isIn) {
        if (value == null || EMPTY.equals(value)) {
            return;
        }
        WHERE(column + MessageFormat.format(isIn ? IN : NOT_IN, value));
    }

    private void handBetween(String column, Object value1, Object value2) {
        if (value1 instanceof String || value1 instanceof Date) {
            WHERE(column + MessageFormat.format(SQL_BETWEEN_AND, QUOTATION + value1 + QUOTATION, QUOTATION + value2 + QUOTATION));
        } else if (value1 instanceof Integer) {
            WHERE(column + MessageFormat.format(SQL_BETWEEN_AND, value1, value2));
        }
    }

    private void handOrderBy(String column){
        OrderBy(column);
    }

    private SQLCondition sql = new SQLCondition();

    private TSqlPlus WHERE(String conditions) {
        sql().listWhere.add(conditions);
        return getSelf();
    }

    private TSqlPlus OrderBy(String orderBy) {
        sql().listOrderBy.add(orderBy);
        return getSelf();
    }

    /**
     * 获取筛选条件
     * @return
     */
    public String getCondition() {
        return sql().buildSqlCondition();
    }

    private SQLCondition sql() {
        return sql;
    }

    private static class SQLCondition implements Serializable {
        List<String> listWhere = new ArrayList<String>();
        List<String> listOrderBy = new ArrayList<String>();

        /**
         * 构建sql搜索条件
         *
         * @return
         */
        private String buildSqlCondition() {
            StringBuffer stringBuffer = new StringBuffer();
            int maxWhere = listWhere.size();
            if (maxWhere > 0) {
                stringBuffer.append(WHERE);
                for (int i = 0; i < maxWhere; i++) {
                    if (i > 0) {
                        stringBuffer.append(AND);
                    }
                    stringBuffer.append(listWhere.get(i));
                }
            }
            int maxOrderBy = listOrderBy.size();
            if (maxOrderBy > 0) {
                stringBuffer.append(ORDER_BY);
                for (int i = 0; i < maxOrderBy; i++) {
                    if (i > 0) {
                        stringBuffer.append(COMMA);
                    }
                    stringBuffer.append(listOrderBy.get(i));
                }
            }
            return stringBuffer.toString();
        }
    }
}
