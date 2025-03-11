package com.tzc.helmiapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class InformationFragment extends Fragment {

    private TextView textViewBodyTemperature;
    private TextView textViewHeartRate;
    private TextView textViewBloodPressure;
    private TextView textViewEnvironmentTemperature;
    private TextView textViewSpeed;
    private TextView textViewFallStress;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            username = bundle.getString("username"); // 从Bundle中获取数据
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Connection connection = DBUtil.getConnection();
                String sql = "SELECT * FROM device_statu WHERE username = ?";

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, username);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        float bodyTemperature = resultSet.getFloat("temperature");
                        float heartRate = resultSet.getFloat("heart_rate");
                        float bloodPressure = resultSet.getFloat("body_pressure");
                        float environmentTemperature = resultSet.getFloat("env_temperature");
                        float speed = resultSet.getFloat("speed");
                        float fallStress = resultSet.getFloat("body_pressure");

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                textViewBodyTemperature.setText(bodyTemperature + "°C");
                                textViewHeartRate.setText(heartRate + "次/分");
                                textViewBloodPressure.setText(bloodPressure + "mmHg");
                                textViewEnvironmentTemperature.setText(environmentTemperature + "°C");
                                textViewSpeed.setText(speed + "m/s");
                                textViewFallStress.setText(fallStress + "Pa");
                            }
                        });
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        textViewBodyTemperature = view.findViewById(R.id.body_temperature_value);
        textViewHeartRate = view.findViewById(R.id.heart_rate_value);
        textViewBloodPressure = view.findViewById(R.id.blood_pressure_value);
        textViewEnvironmentTemperature = view.findViewById(R.id.environment_temperature_value);
        textViewSpeed = view.findViewById(R.id.speed_value);
        textViewFallStress = view.findViewById(R.id.fall_stress_value);
        return view;
    }
}