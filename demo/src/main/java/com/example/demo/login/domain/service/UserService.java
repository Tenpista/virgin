package com.example.demo.login.domain.service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Transactional
@Service
public class UserService {

    @Autowired
    @Qualifier("UserDaoJdbcImpl")
    UserDao dao;

    public boolean insert(User user) {

        int rowNumber = dao.insertOne(user);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    // カウントメソッド呼び出し
    public int count() {
        return dao.count();
    }

    // 全件取得用ソッド呼び出し
    public List<User> selectMany() {
        return dao.selectMany();
    }

    // 1件取得メソッド
    public User selectOne(String userId) {

        return dao.selectOne(userId);
    }

    // 1件更新メソッド
    public boolean updateOne(User user) {

        // 1件更新
        int rowNumber = dao.updateOne(user);

        // 判定用変数
        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }

        return result;
    }

    // 1件削除メソッド
    public boolean deleteOne(String userId) {

        // 1件削除
        int rowNumber = dao.deleteOne(userId);

        // 判定用変数
        boolean result = false;

        if (rowNumber > 0) {
            // delete成功
            result = true;
        }
        return result;
    }

    // ユーザー一覧をCSV出力する
    public void userCsvOut() throws DataAccessException {

        // CSV出力
        dao.userCsvOut();
    }

    // サーバーに保存されているファイルを取得してbyte配列に変換する
    public byte[] getFile(String fileName) throws IOException {

        // ファイルシステム(デフォルト)の取得
        FileSystem fs = FileSystems.getDefault();

        // ファイル取得
        Path p = fs.getPath(fileName);

        // ファイルをbyte配列に変換
        byte[] bytes = Files.readAllBytes(p);

        return bytes;
    }
}
