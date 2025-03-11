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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void goToRegisterView(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        String username = ((EditText) findViewById(R.id.edt_login_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.edt_login_password)).getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = DBUtil.getConnection();

                // 判断是否该用户是否存在
                String checkUserExists = "SELECT username FROM user WHERE username = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(checkUserExists);
                    preparedStatement.setString(1, username);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (!resultSet.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // 判断密码是否正确
                String checkPasswordIsCorrect = "SELECT username FROM user WHERE username = ? AND password = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(checkPasswordIsCorrect);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (!resultSet.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // 登录成功
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        }).start();
    }
}