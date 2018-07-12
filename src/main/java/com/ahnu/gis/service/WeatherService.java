package com.ahnu.gis.service;

import java.io.File;

public interface WeatherService {

    int importExcel(File file);

    int updateWeatherReverseBatch();
}
