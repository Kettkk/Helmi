package com.tzc.helmi.entity;

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

    // Getter 和 Setter 方法
    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIs_wear() {
        return is_wear;
    }

    public void setIs_wear(String is_wear) {
        this.is_wear = is_wear;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getEnv_temperature() {
        return env_temperature;
    }

    public void setEnv_temperature(float env_temperature) {
        this.env_temperature = env_temperature;
    }

    public float getEnv_humidity() {
        return env_humidity;
    }

    public void setEnv_humidity(float env_humidity) {
        this.env_humidity = env_humidity;
    }

    public float getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(float heart_rate) {
        this.heart_rate = heart_rate;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getHigh_pressure() {
        return high_pressure;
    }

    public void setHigh_pressure(float high_pressure) {
        this.high_pressure = high_pressure;
    }

    public float getLow_pressure() {
        return low_pressure;
    }

    public void setLow_pressure(float low_pressure) {
        this.low_pressure = low_pressure;
    }

    public float getBody_pressure() {
        return body_pressure;
    }

    public void setBody_pressure(float body_pressure) {
        this.body_pressure = body_pressure;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Statu{" +
                "device_id=" + device_id +
                ", user_id=" + user_id +
                ", username='" + username + '\'' +
                ", is_wear='" + is_wear + '\'' +
                ", temperature=" + temperature +
                ", env_temperature=" + env_temperature +
                ", env_humidity=" + env_humidity +
                ", heart_rate=" + heart_rate +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", high_pressure=" + high_pressure +
                ", low_pressure=" + low_pressure +
                ", body_pressure=" + body_pressure +
                ", speed=" + speed +
                '}';
    }
}