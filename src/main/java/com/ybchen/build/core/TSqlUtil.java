package com.ybchen.build.core;

public interface TSqlUtil {
    String IS_NOT_NULL = " is not null";
    String IS_NULL = " is null";
    String SQL_FULL_LIKE = " {0} like concat({1},{2},{3})";
    String SQL_LEFT_OR_RIGHT_LIKE = " {0} like concat({1},{2})";
    String SQL_BETWEEN_AND = " between {0} and {1}";
    String IN = " in ({0})";
    String NOT_IN = " not" + IN;
    String QUOTATION_MARK = "'";
    String INSERT = "insert";
    String IN_TO=" into ";
    String DELETE = "delete";
    String UPDATE = "update ";
    String SELECT = "select ";
    String FROM = " from ";
    String WHERE = " where ";
    String LIMIT = " limit ";
    String LIMIT_ONE = " limit 1";
    String EQUAL = " = ";
    String PERCENT = "%";
    String QUOTATION = "'";
    String COMMA = ",";
    String COUNT = "count(1)";
    String AND = " and ";
    String ORDER_BY = " order by ";
    String EMPTY = "";
    String VALUES=" values";
    String LEFT_BRACKET=" (";
    String RIGHT_BRACKET=")";
    String NULL="NULL";
    String QUESTION_MARK="?";
    String SEMICOLON=";";
    String SET=" SET ";
    String SET_VALUE_TEMPLATE=EQUAL+QUESTION_MARK+COMMA;
}
