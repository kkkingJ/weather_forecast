package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
//import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

// com.example.weatherforecast.gson.Forecast;
import com.example.weatherforecast.forecastgson.Daily;
import com.example.weatherforecast.forecastgson.Forecast;
import com.example.weatherforecast.weathergson.Weather;
import com.example.weatherforecast.util.HttpUtil;
import com.example.weatherforecast.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView degreeText;
    private TextView weatherInfoText;

    private TextView humidityText;
    private TextView obstimeText;
    private TextView feelslikeText;
    private TextView dewText;
    private TextView wind360Text;
    private TextView winddirText;
    private TextView windscaleText;
    private TextView windspeedText;
    private TextView precipText;
    private TextView pressureText;
    private TextView visText;
    private TextView cloudText;
    private TextView updateText;
    private LinearLayout forecastLayout;
    public SwipeRefreshLayout swipeRefresh;
    private String mWeatherId;
    private String mcityName;

    public DrawerLayout drawerLayout;
    private Button cityChangeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // 初始化各控件
        weatherLayout = findViewById(R.id.weather_layout);
        titleCity = findViewById(R.id.title_city);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        humidityText = findViewById(R.id.humidity_text);
        obstimeText = findViewById(R.id.obstime_text);
        feelslikeText = findViewById(R.id.feelslike_text);
        dewText = findViewById(R.id.dew_text);
        wind360Text = findViewById(R.id.wind360_text);
        winddirText = findViewById(R.id.winddir_text);
        windscaleText = findViewById(R.id.windscale_text);
        windspeedText = findViewById(R.id.windspeed_text);
        precipText = findViewById(R.id.precip_text);
        pressureText = findViewById(R.id.pressure_text);
        visText = findViewById(R.id.vis_text);
        cloudText = findViewById(R.id.cloud_text);
        //sourcesText = findViewById(R.id.sources_text);
        //updateText = findViewById(R.id.updatetime_text);
        drawerLayout = findViewById(R.id.drawer_layout);
        cityChangeBtn = findViewById(R.id.citychange_btn);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        swipeRefresh.setColorSchemeResources(R.color.black);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);

        /**
         * 按钮点击事件
         */
        cityChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
                drawerLayout.setScrimColor(Color.WHITE);
            }
        });

        /**
         * 缓存处理
         */
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences
                (this);
        String weatherString = prefs.getString("weather", null);
        String forecastString = prefs.getString("forecast", null);
        String citynameString = prefs.getString("cityname",null);
        if (weatherString != null && forecastString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            Forecast forecast = Utility.handleForecastResponse(forecastString);
            showWeatherInfo(weather,citynameString);
            showForecastInfo(forecast);
        } else {
            // 无缓存时去服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");
            mcityName = getIntent().getStringExtra("city_name");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId,mcityName);
        }

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId,mcityName);
                Toast.makeText(WeatherActivity.this, "更新成功",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 根据天气 id 请求城市天气信息
     */
    public void requestWeather(final String weatherId,final String cityName) {

        String weatherUrl = "https://devapi.qweather.com/v7/weather/now?location=" +
                weatherId + "&key=1c9b9e1c24774e278e5ba7972949c9f7";

        String forecastUrl = "https://devapi.qweather.com/v7/weather/3d?location=" + weatherId +
                "&key=1c9b9e1c24774e278e5ba7972949c9f7" ;

        Log.d("------------------------------","forecastUrl="+forecastUrl);

        //获取并解析实时天气jason数据
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                Log.d("----------------------------","weather:"+weather);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (weather != null && "200".equals(weather.code)) {
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).
                                    edit();
                            editor.putString("weather", responseText);
                            editor.putString("cityname",cityName);

                            editor.apply();

                            showWeatherInfo(weather,cityName);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败1",
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);

                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败2",
                                Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });


        //获取并解析天气预报jason数据
        HttpUtil.sendOkHttpRequest(forecastUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Forecast forecast = Utility.handleForecastResponse(responseText);
                Log.d("----------------------------","forecast:"+forecast);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (forecast != null && "200".equals(forecast.code)) {
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).
                                    edit();
                            editor.putString("forecast", responseText);
                            editor.apply();
                            showForecastInfo(forecast);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败1",
                                    Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);

                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败2",
                                Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });


    }
    /**
     * 处理并展示 Weather 实体类中的数据
     */
    private void showWeatherInfo(Weather weather,String cityName) {
        String degree = weather.now.temp + "℃";
        String weatherInfo = weather.now.text;
        String updateTime = "数据观测时间："+weather.updateTime;
        String feelslike = weather.now.feelsLike+ "℃";
        String wind360 = weather.now.wind360;
        String windScale = weather.now.windScale;
        String windSpeed = weather.now.windSpeed;
        String windDir = weather.now.windDir;
        String humidity = weather.now.humidity+"%";
        String precip = weather.now.precip;
        String pressure = weather.now.pressure;
        String vis = weather.now.vis;
        String cloud = weather.now.cloud;
        String dew = weather.now.dew+ "℃";
       // String updateTime = "预计下次更新时间："+weather.updateTime;

        titleCity.setText(cityName);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        humidityText.setText(humidity);
        obstimeText.setText(updateTime);
        feelslikeText.setText(feelslike);
        dewText.setText(dew);
        wind360Text.setText(wind360);
        winddirText.setText(windDir);
        windscaleText.setText(windScale);
        windspeedText.setText(windSpeed);
        precipText.setText(precip);
        pressureText.setText(pressure);
        visText.setText(vis);
        cloudText.setText(cloud);
       // updateText.setText(updateTime);

        weatherLayout.setVisibility(View.VISIBLE);
    }
    /**
     * 处理并展示 Forecast 实体类中的数据
     */
    private void showForecastInfo(Forecast forecast) {
        forecastLayout.removeAllViews();

        for(Daily daily : forecast.dailyList){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(daily.fxDate);
            infoText.setText(daily.textDay);
            maxText.setText(daily.tempMax);
            minText.setText(daily.tempMin);
            forecastLayout.addView(view);
        }

}
}
