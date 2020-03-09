package com.example.demo.login.domain.repository.jdbc;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("UserDaoNamedJdbcImpl")
public class UserDaoNamedJdbcImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    // Userテーブルの件数を取得
    @Override
    public int count() {

        // SQL文
        String sql = "SELECT COUNT(*) FROM m_user";

        // パラメーター生成
        SqlParameterSource params = new MapSqlParameterSource();

        // 全件取得してカウント
        return jdbc.queryForObject(sql, params, Integer.class);
    }

    // Userテーブルにデータを1件insert
    @Override
    public int insertOne(User user) {

        // ポイント1 : SQL文にキー名を指定
        // SQL文
        String sql = "INSERT INTO m_user(user_id,"
                + "password,"
                + "user_name,"
                + "birthday,"
                + "age,"
                + "marriage,"
                + "role)"
                + "VALUES(:userId,"
                + ":password,"
                + ":userName,"
                + ":birthday,"
                + ":age,"
                + ":marriage,"
                + ":role)";

        // ポイント２パラメーターの設定
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("password", user.getPassword())
                .addValue("userName", user.getUserName())
                .addValue("birthday", user.getBirthday())
                .addValue("age", user.getAge())
                .addValue("marriage", user.isMarriage())
                .addValue("role", user.getRole());

        // SQL実行
        return jdbc.update(sql, params);
    }

    // Userテーブルのデータを1件取得
    @Override
    public User selectOne(String userId) {

        // SQL文
        String sql = "SELECT * FROM m_user WHERE user_id = :userId";

        // SQL実行
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", userId);

        Map<String, Object> map = jdbc.queryForMap(sql, param);

        // Userインスタンスに格納
        User user = new User();

        user.setUserId((String) map.get("user_id"));
        user.setPassword((String) map.get("password"));
        user.setUserName((String) map.get("user_name"));
        user.setBirthday((Date) map.get("birthday"));
        user.setAge((Integer) map.get("age"));
        user.setMarriage((Boolean) map.get("marriage"));
        user.setRole((String) map.get("role"));

        return user;
    }

    // Userテーブルの全データを取得
    @Override
    public List<User> selectMany() {

        // SQL文
        String sql = "SELECT * FROM m_user";

        // パラメーター
        SqlParameterSource params = new MapSqlParameterSource();

        // SQL実行
        List<Map<String, Object>> getList = jdbc.queryForList(sql, params);

        // 返却する箱用意
        List<User> userList = new ArrayList<>();

        // for文を使いList<>の中にあるレコード(Map<>)をあるだけ格納
        for (Map<String, Object> map : getList) {

            // 各レコード情報を格納するインスタンス生成
            User user = new User();

            // インスタンスにデータをセット
            user.setUserId((String) map.get("user_id"));
            user.setPassword((String) map.get("password7"));
            user.setUserName((String) map.get("user_name"));
            user.setBirthday((Date) map.get("birthday"));
            user.setAge((Integer) map.get("age"));
            user.setMarriage((Boolean) map.get("marriage"));
            user.setRole((String) map.get("role"));

            // 一つ分のレコードを格納
            userList.add(user);
        }
        return userList;
    }

    // Userテーブルを1件更新
    @Override
    public int updateOne(User user) {

        // SQL文
        String sql = "UPDATE m_user SET"
                + " password = :password,"
                + " user_name = :userName,"
                + " birthday = :birthday,"
                + " age = :age,"
                + " marriage = :marriage "
                + "WHERE user_id = :userId";

        // パラメ
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("password", user.getPassword())
                .addValue("userName", user.getUserName())
                .addValue("birthday", user.getBirthday())
                .addValue("age", user.getAge())
                .addValue("marriage", user.isMarriage());

        // SQL実行
        return jdbc.update(sql, params);
    }

    // Userテーブルを1件削除
    @Override
    public int deleteOne(String userId) {

        // SQL文
        String sql = "DELETE FROM m_user WHERE user_id = :userId";

        // パラメ
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);

        // SQL実行
        int rowNumber = jdbc.update(sql, params);

        return rowNumber;
    }

    // Userテーブルの全データをCSVに出力する
    @Override
    public void userCsvOut() {

        // SQL文
        String sql = "SELECT * FROM m_user";

        // RowCallBackHandlerの生成
        UserRowCallBackHandler handler = new UserRowCallBackHandler();

        // SQL実行&CSV出力
        jdbc.query(sql, handler);
    }
}
