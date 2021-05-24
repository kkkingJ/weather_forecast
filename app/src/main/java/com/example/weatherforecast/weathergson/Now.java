package com.example.weatherforecast.weathergson;


public class Now {
    /**
     * obsTime : 2020-07-15T07:35+08:00
     * temp : 27
     * feelsLike : 29
     * icon : 100
     * text : 晴
     * wind360 : 209
     * windDir : 西南风
     * windScale : 1
     * windSpeed : 4
     * humidity : 67
     * precip : 0.0
     * pressure : 1001
     * vis : 11
     * cloud : 0
     * dew : 20
     */
    public String obsTime;
    public String temp;
    public String feelsLike;
    public String icon;
    public String text;
    public String wind360;
    public String windDir;
    public String windScale;
    public String windSpeed;
    public String humidity;
    public String precip;
    public String pressure;
    public String vis;
    public String cloud;
    public String dew;

    @Override
    public String toString() {
        return "Now{" +
                "obsTime='" + obsTime + '\'' +
                ", temp='" + temp + '\'' +
                ", feelsLike='" + feelsLike + '\'' +
                ", icon='" + icon + '\'' +
                ", text='" + text + '\'' +
                ", wind360='" + wind360 + '\'' +
                ", windDir='" + windDir + '\'' +
                ", windScale='" + windScale + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", humidity='" + humidity + '\'' +
                ", precip='" + precip + '\'' +
                ", pressure='" + pressure + '\'' +
                ", vis='" + vis + '\'' +
                ", cloud='" + cloud + '\'' +
                ", dew='" + dew + '\'' +
                '}';
    }
}
