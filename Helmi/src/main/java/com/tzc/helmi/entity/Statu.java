package com.tzc.helmi.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Statu {
    private Integer device_id;
    private Integer user_id;
    private String username;
    private String is_wear;
    private float temperature;
    private float env_temperature;
    private float env_humidity;
    private float heart_rate;
    private float longitude;
    private float latitude;
    private float high_pressure;
    private float low_pressure;
    private float body_pressure;
    private float speed;
    private LocalDateTime create_time;
    private LocalDateTime last_update_time;
}
