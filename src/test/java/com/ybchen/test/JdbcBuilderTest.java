package com.ybchen.test;

import com.ybchen.build.core.Pages;
import com.ybchen.build.core.TSqlPlus;
import com.ybchen.test.config.JdbcTest;
import com.ybchen.test.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName：Test04
 * @Description：查询测试
 * @Author：chenyb
 * @Date：2020/9/29 4:21 下午
 * @Versiion：1.0
 */
public class JdbcBuilderTest extends JdbcTest {
    @org.junit.Test
    public void selectOne() {
        TSqlPlus tSqlPlus = new TSqlPlus();
        User user = jdbcBean.selectOne(User.class, tSqlPlus);
        System.out.println(user);

    }

    @org.junit.Test
    public void selectList() {
        TSqlPlus tSqlPlus = new TSqlPlus();
        tSqlPlus.AND("id", "1");
        List<User> users = jdbcBean.selectList(User.class, tSqlPlus);
        System.out.println(users);
    }

    @org.junit.Test
    public void selectPage() {
        int start = 1;
        int end = 3;
        TSqlPlus tSqlPlus = new TSqlPlus();
        tSqlPlus.AND("id", "1");
        Pages<User> table1Pages = jdbcBean.selectPage(User.class, tSqlPlus, start, end);
        System.out.println("集合：" + table1Pages.getList());
        System.out.println("总行数:" + table1Pages.getTotal());
        System.out.println("总页数:" + table1Pages.getTotalPages());
        System.out.println("当前页:" + table1Pages.getCurrentPage());
        System.out.println("页的大小:" + table1Pages.getPageSize());
    }

    @org.junit.Test
    public void deleteById() {
        int num = jdbcBean.deleteById(User.class, 13);
        System.out.println(num);
    }

    @org.junit.Test
    public void deleteByCondition() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(11);
        list.add(12);
        TSqlPlus tSqlPlus = new TSqlPlus();
        tSqlPlus.IN("id", list);
        int num = jdbcBean.deleteByCondition(User.class, tSqlPlus);
        System.out.println(num);
    }

    @org.junit.Test
    public void deleteAll() {
        int num = jdbcBean.deleteAll(User.class);
        System.out.println(num);
    }

    @org.junit.Test
    public void insert() {
        User user=new User();
        user.setAge(18);
        user.setCreateDate(new Date());
        user.setUserName("alex");
        System.out.println(jdbcBean.insert(User.class, user));
    }

    @org.junit.Test
    public void update() {
        User user=new User();
        user.setId("6");
        user.setAge(233);
        user.setUserName("");
        user.setCreateDate(null);
//        user.setUserName("alexalex");
        System.out.println(jdbcBean.update(User.class, user));
    }
}
