package com.tzc.helmiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        String username = ((EditText) findViewById(R.id.edt_register_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.edt_register_password)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.edt_register_confirm_password)).getText().toString();

        // 判断两次密码是否输入一致
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = DBUtil.getConnection();

                // 判断用户名是否已存在
                String checkUserExists = "SELECT username FROM user WHERE username = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(checkUserExists);
                    preparedStatement.setString(1, username);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                // 注册成功
                String addUser = "INSERT INTO user (username, password) VALUES (?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(addUser);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.execute();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}