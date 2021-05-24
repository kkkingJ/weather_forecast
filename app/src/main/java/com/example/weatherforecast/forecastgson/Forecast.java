package com.example.weatherforecast.forecastgson;

import com.example.weatherforecast.weathergson.Refer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast {
    public String code;
    public String updateTime;
    public String fxLink;
    @SerializedName("daily")
    public List<Daily> dailyList;
    public Refer refer;


    @Override
    public String toString() {
        return "Forecast{" +
                "code='" + code + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", fxLink='" + fxLink + '\'' +
                ", dailyList=" + dailyList +
                ", refer=" + refer +
                '}';
    }
}
