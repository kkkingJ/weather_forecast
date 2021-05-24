package com.example.weatherforecast.weathergson;

public class Weather {
    public String code;
    public String updateTime;
    public String fxLink;
    public Now now;
    public Refer refer;


    @Override
    public String toString() {
        return "Weather{" +
                "code='" + code + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", fxLink='" + fxLink + '\'' +
                ", now=" + now +
                ", refer=" + refer +
                '}';
    }
}
