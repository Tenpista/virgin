package com.example.demo.login.domain.repository.jdbc;

import com.example.demo.login.domain.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {

    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {

        // User格納用List
        List<User> userList = new ArrayList<>();

        // 取得件数分のloop
        while(rs.next()){

            // List に追加するインスタンスを生成する
            User user = new User();

            // 取得したレコードをUserインスタンスにセット
            user.setUserId(rs.getString("user_Id"));
            user.setPassword(rs.getString("password"));
            user.setUserName(rs.getString("user_name"));
            user.setBirthday(rs.getDate("birthday"));
            user.setAge(rs.getInt("age"));
            user.setMarriage(rs.getBoolean("marriage"));
            user.setRole(rs.getString("role"));

            // ListにUserを追加
            userList.add(user);
        }

        // 1件もなかった場合は例外を投げる
        if(userList.size() == 0){
            throw new EmptyResultDataAccessException(1);
        }

        return userList;
    }
}
