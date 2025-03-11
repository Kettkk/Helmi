package com.tzc.helmiapp;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String TAG = "DBUtil";

    public static Connection getConnection() {
        String url = "jdbc:mysql://114.55.40.205:3306/Helmi?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
        String username = "root";
        String password = "802642wzxgx";
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.i(TAG, "加载JDBC驱动成功");
        } catch (ClassNotFoundException e) {
            Log.i(TAG, "加载JDBC驱动失败");
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
            Log.i(TAG, "数据库连接成功");
        } catch (SQLException e) {
            Log.i(TAG, "数据库连接失败");
        }

        return connection;
    }
}
