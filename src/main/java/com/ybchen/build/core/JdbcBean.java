package com.ybchen.build.core;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.ybchen.build.delete.DeleteBuild;
import com.ybchen.build.insert.InsertBuild;
import com.ybchen.build.select.SelectBuild;
import com.ybchen.build.update.UpdateBuild;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName：JdbcBean
 * @Description：JdbcBean
 * @Author：chenyb
 * @Date：2020/9/28 5:21 下午
 * @Versiion：1.0
 */
public class JdbcBean {
    private JdbcTemplate jdbcTemplate;

    public Boolean excuteDDL(String createTableSql) {
        try {
            jdbcTemplate.execute(createTableSql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T> T selectOne(Class<T> clazz, TSqlPlus tSqlPlus) {
        return jdbcTemplate.queryForObject(new SelectBuild().buildSelectOne(clazz, tSqlPlus), new EntityToRowMapper<>(clazz));
    }

    public <T> List<T> selectList(Class<T> clazz, TSqlPlus tSqlPlus) {
        return jdbcTemplate.query(new SelectBuild().buildSelectList(clazz, tSqlPlus), new EntityToRowMapper<>(clazz));
    }

    public <T> Pages<T> selectPage(Class<T> clazz, TSqlPlus tSqlPlus, int start, int end) {
        List<Integer> totalList = jdbcTemplate.query(new SelectBuild().buildSelectCount(clazz, tSqlPlus), new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt(1);
            }
        });
        List list = jdbcTemplate.query(new SelectBuild().buildSelectPage(clazz, tSqlPlus, start, end), new EntityToRowMapper<>(clazz));
        Pages<T> objectPages = new Pages<>(totalList.get(0), start, end, list);
        return objectPages;
    }

    public <T> int deleteById(Class<T> clazz, Object object) {
        return jdbcTemplate.update(new DeleteBuild().buildDeleteById(clazz, object));
    }

    public <T> int deleteByCondition(Class<T> clazz, TSqlPlus tSqlPlus) {
        return jdbcTemplate.update(new DeleteBuild().buildDeleteByCondition(clazz, tSqlPlus));
    }

    public <T> int deleteAll(Class<T> clazz) {
        return jdbcTemplate.update(new DeleteBuild().buildDeleteAll(clazz));
    }

    public <T> int insert(Class<?> clazz, T entity) {
        InsertBuild build = new InsertBuild();
        return jdbcTemplate.update(build.buildInsert(clazz, entity), build.getObjects());
    }

    public <T> int update(Class<?> clazz, T entity) {
        UpdateBuild build=new UpdateBuild();
        return jdbcTemplate.update(build.buildUpdate(clazz,entity),build.getObjects());
    }

    public int executeSql(String strSql) {
        return jdbcTemplate.update(strSql);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
