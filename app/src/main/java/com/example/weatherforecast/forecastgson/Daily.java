package com.example.weatherforecast.forecastgson;

public class Daily {
    public String fxDate;
    public String sunrise;
    public String sunset;
    public String moonrise;
    public String moonset;
    public String moonPhase;
    public String tempMax;
    public String tempMin;
    public String iconDay;
    public String textDay;
    public String iconNight;
    public String textNight;
    public String wind360Day;
    public String windDirDay;
    public String windScaleDay;
    public String windSpeedDay;
    public String wind360Night;
    public String windDirNight;
    public String windScaleNight;
    public String windSpeedNight;
    public String humidity;
    public String precip;
    public String pressure;
    public String vis;
    public String cloud;
    public String uvIndex;

    @Override
    public String toString() {
        return "Daily{" +
                "fxData='" + fxDate + '\'' +
                ", sunset='" + sunset + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", moonrise='" + moonrise + '\'' +
                ", moonset='" + moonset + '\'' +
                ", moonPhase='" + moonPhase + '\'' +
                ", tempMax='" + tempMax + '\'' +
                ", tempMin='" + tempMin + '\'' +
                ", iconDay='" + iconDay + '\'' +
                ", textDay='" + textDay + '\'' +
                ", iconNight='" + iconNight + '\'' +
                ", textNight='" + textNight + '\'' +
                ", wind360Day='" + wind360Day + '\'' +
                ", windDirDay='" + windDirDay + '\'' +
                ", windScaleDay='" + windScaleDay + '\'' +
                ", windSpeedDay='" + windSpeedDay + '\'' +
                ", wind360Night='" + wind360Night + '\'' +
                ", windDirNight='" + windDirNight + '\'' +
                ", windScaleNight='" + windScaleNight + '\'' +
                ", windSpeedNight='" + windSpeedNight + '\'' +
                ", humidity='" + humidity + '\'' +
                ", precip='" + precip + '\'' +
                ", pressure='" + pressure + '\'' +
                ", vis='" + vis + '\'' +
                ", cloud='" + cloud + '\'' +
                ", uvIndex='" + uvIndex + '\'' +
                '}';
    }
}
