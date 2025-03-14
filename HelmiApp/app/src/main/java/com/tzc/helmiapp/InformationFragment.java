package com.tzc.helmiapp;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
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
    private TextView textViewIsWear;
    private TextView textViewTemperature;
    private TextView textViewEnvironmentTemperature;
    private TextView textViewEnvironmentHumidity;
    private TextView textViewHeartRate;
    private TextView textViewLongitude;
    private TextView textViewLatitude;
    private TextView textViewHighPressure;
    private TextView textViewLowPressure;
    private TextView textViewFallStress;
    private TextView textViewSpeed;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取设备数据
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
                        int isWear = resultSet.getInt("is_wear");
                        float temperature = resultSet.getFloat("temperature");
                        float environmentTemperature = resultSet.getFloat("env_temperature");
                        float environmentHumidity = resultSet.getFloat("env_humidity");
                        float heartRate = resultSet.getFloat("heart_rate");
                        float longitude = resultSet.getFloat("longitude");
                        float latitude = resultSet.getFloat("latitude");
                        float highPressure = resultSet.getFloat("high_pressure");
                        float lowPressure = resultSet.getFloat("low_pressure");
                        float fallStress = resultSet.getFloat("body_pressure");
                        float speed = resultSet.getFloat("speed");


                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if (isWear == 0) {
                                    textViewIsWear.setText("否");
                                } else {
                                    textViewIsWear.setText("是");
                                }

                                textViewTemperature.setText(temperature + "°C");
                                textViewEnvironmentTemperature.setText(environmentTemperature + "°C");
                                textViewEnvironmentHumidity.setText(environmentHumidity + "%");
                                textViewHeartRate.setText(heartRate + "bpm");
                                textViewLongitude.setText(longitude + "°");
                                textViewLatitude.setText(latitude + "°");
                                textViewHighPressure.setText(highPressure + "mmHg");
                                textViewLowPressure.setText(lowPressure + "mmHg");
                                textViewFallStress.setText(fallStress + "Pa");
                                textViewSpeed.setText(speed + "m/s");

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
        textViewIsWear = view.findViewById(R.id.is_wear_value);
        textViewTemperature = view.findViewById(R.id.temperature_value);
        textViewEnvironmentTemperature = view.findViewById(R.id.environment_temperature_value);
        textViewEnvironmentHumidity = view.findViewById(R.id.environment_humidity_value);
        textViewHeartRate = view.findViewById(R.id.heart_rate_value);
        textViewLongitude = view.findViewById(R.id.longitude_value);
        textViewLatitude = view.findViewById(R.id.latitude_value);
        textViewHighPressure = view.findViewById(R.id.high_pressure_value);
        textViewLowPressure = view.findViewById(R.id.low_pressure_value);
        textViewFallStress = view.findViewById(R.id.fall_stress_value);
        textViewSpeed = view.findViewById(R.id.speed_value);


        return view;
    }

}