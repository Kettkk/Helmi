package com.tzc.helmiapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingsFragment extends Fragment {
    private static final String TAG = SettingsFragment.class.getSimpleName();

    private TextView tvManagerUsername;
    private TextView tvManagerName;
    private TextView tvManagerGender;
    private TextView tvManagerPhone;
    private TextView tvManagerEmail;
    private EditText editPassword;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            username = bundle.getString("username"); // 从Bundle中获取数据
            Log.i(TAG, "onCreate: " + username);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = DBUtil.getConnection();
                String sql = "SELECT * FROM user WHERE username = ?";

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, username);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        String userUsername = resultSet.getString("username");
                        String name = resultSet.getString("name");
                        String gender = resultSet.getString("sex");
                        String phone = resultSet.getString("phone");
                        String email = resultSet.getString("email");
                        Log.i(TAG, "onCreate: " + userUsername + name + gender + phone + email);

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (tvManagerUsername != null) {
                                    tvManagerUsername.setText(userUsername);
                                }
                                if (tvManagerName != null) {
                                    tvManagerName.setText(name);
                                }
                                if (tvManagerGender != null) {
                                    tvManagerGender.setText(gender);
                                }
                                if (tvManagerPhone != null) {
                                    tvManagerPhone.setText(phone);
                                }
                                if (tvManagerEmail != null) {
                                    tvManagerEmail.setText(email);
                                }
                            }
                        });
                    }

                } catch (SQLException e) {
                    Log.e(TAG, "数据库查询出错: " + e.getMessage());
                } finally {
                    try {
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        Log.e(TAG, "数据库连接关闭出错: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        tvManagerUsername = view.findViewById(R.id.tv_manager_username);
        tvManagerName = view.findViewById(R.id.tv_manager_name);
        tvManagerGender = view.findViewById(R.id.tv_manager_gender);
        tvManagerPhone = view.findViewById(R.id.tv_manager_phone);
        tvManagerEmail = view.findViewById(R.id.tv_manager_email);
        editPassword = view.findViewById(R.id.edit_password);

        // 为按钮设置点击监听器
        view.findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        return view;
    }

    private void changePassword() {
        if (editPassword == null) {
            Log.e(TAG, "editPassword 未初始化");
            Toast.makeText(getActivity(), "发生错误，请稍后重试", Toast.LENGTH_SHORT).show();
            return;
        }
        String newPassword = editPassword.getText().toString().trim();

        if (newPassword.isEmpty()) {
            Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = DBUtil.getConnection();
                String sql = "UPDATE user SET password = ? WHERE username = ?";

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, newPassword);
                    preparedStatement.setString(2, username);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "密码修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                                // 跳转到登录界面
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "密码修改失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (SQLException e) {
                    Log.e(TAG, "数据库更新出错: " + e.getMessage());
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "数据库更新出错", Toast.LENGTH_SHORT).show();
                        }
                    });
                } finally {
                    try {
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        Log.e(TAG, "数据库连接关闭出错: " + e.getMessage());
                    }
                }
            }
        }).start();
    }
}