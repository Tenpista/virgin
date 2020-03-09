package com.example.demo.login.domain.repository.jdbc;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    PasswordEncoder passwordEncoder;

    // Userテーブルの件数を取得
    @Override
    public int count() throws DataAccessException {

        // Objectの取得
        // 全件取得してカウント
        int count = jdbc.queryForObject("SELECT COUNT(*) FROM m_user", Integer.class);
        return count;
    }

    // Userテーブルにデータを1件insert
    @Override
    public int insertOne(User user) throws DataAccessException {

        // パスワード暗号化
        String password = passwordEncoder.encode(user.getPassword());

        // 1件登録
        int rowNumber = jdbc.update("INSERT INTO m_user(user_id,"
                        + "password,"
                        + "user_name,"
                        + "birthday,"
                        + "age,"
                        + "marriage,"
                        + "role)"
                        + "VALUES(?,?,?,?,?,?,?)"
                , user.getUserId()
                , password
                , user.getUserName()
                , user.getBirthday()
                , user.getAge()
                , user.isMarriage()
                , user.getRole());
        return rowNumber;
    }

    // Userテーブルのデータを1件取得
    @Override
    public User selectOne(String userId) throws DataAccessException {

        // 1件取得
        Map<String, Object> map = jdbc.queryForMap("SELECT * FROM m_user"
                + " WHERE user_id = ?", userId);

        // 結果返却用の変数
        User user = new User();

        // 取得したデータを結果返却用の変数にセットしていく
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
    public List<User> selectMany() throws DataAccessException {

        // 複数県のselect
        // m_userテーブルのデータを全件取得
        // Map<"カラム名","値">でセットされる
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM m_user");

        // ここに全レコードを入れてreturnする
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
    public int updateOne(User user) throws DataAccessException {

        // パスワード暗号化
        String password = passwordEncoder.encode(user.getPassword());

        // 1件更新
        int rowNumber = jdbc.update("UPDATE m_user SET"
                        + " password = ?,"
                        + " user_name = ?,"
                        + " birthday = ?,"
                        + " age = ?,"
                        + " marriage = ?"
                        + " WHERE user_id = ?"
                , password
                , user.getUserName()
                , user.getBirthday()
                , user.getAge()
                , user.isMarriage()
                , user.getUserId());

/*        // トランザクション確認のため、わざと例外をthrowする
        if(rowNumber > 0){
            throw new DataAccessException("トランザクションテスト"){};
        }*/

        return rowNumber;
    }

    // Userテーブルを1件削除
    @Override
    public int deleteOne(String userId) throws DataAccessException {

        // 1件削除
        int rowNumber = jdbc.update("DELETE FROM m_user WHERE user_id = ?", userId);

        return rowNumber;
    }

    // Userテーブルの全データをCSVに出力する
    @Override
    public void userCsvOut() throws DataAccessException {

        // M_Userテーブルのデータを全件取得するSQL
        String sql = "SELECT * FROM m_user";

        // RowCallBackHandlerの生成
        UserRowCallBackHandler handler = new UserRowCallBackHandler();

        // SQL実行&CSV出力
        jdbc.query(sql, handler);
    }
}
