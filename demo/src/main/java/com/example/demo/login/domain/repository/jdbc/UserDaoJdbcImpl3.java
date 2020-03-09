package com.example.demo.login.domain.repository.jdbc;

import com.example.demo.login.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserDaoJdbcImpl3")
public class UserDaoJdbcImpl3 extends UserDaoJdbcImpl{

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public User selectOne(String userId){

        // 1件取得用SQL
        String sql = "SELECT * FROM m_user WHERE user_id = ?";

        // RowMapperの生成
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);

        // SQL実行
        return jdbc.queryForObject(sql,rowMapper, userId);
    }

    @Override
    public List<User> selectMany(){

        // m_userテーブルのデータを全件取得するSQL
        String sql = "SELECT * FROM m_user";

        // RowMapperの生成
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);

        // SQL実行
        return jdbc.query(sql, rowMapper);
    }
}
