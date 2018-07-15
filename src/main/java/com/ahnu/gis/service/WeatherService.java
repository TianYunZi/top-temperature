package com.ahnu.gis.service;

import com.ahnu.gis.model.Weather;

import java.io.File;

public interface WeatherService {

    Weather selectByPrimaryKey(Integer id);

    int importExcel(File file);

    int updateWeatherReverseBatch();
}
