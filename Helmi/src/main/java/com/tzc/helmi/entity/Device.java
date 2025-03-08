package com.tzc.helmi.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Device {

    private Integer device_id;

    private Integer user_id;

    private String username;

    private float longitude;

    private float latitude;

    private LocalDateTime create_time;

    private LocalDateTime last_update_time;
}
