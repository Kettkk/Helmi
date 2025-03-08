package com.tzc.helmi.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {

    private Integer user_id;

    private String username;

    private String password;

    private String name;

    private String phone;

    private String sex;

    private String email;

    private LocalDateTime create_time;

    private LocalDateTime last_update_time;
}