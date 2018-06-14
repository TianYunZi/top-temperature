package com.ahnu.gis.dao;

import com.ahnu.gis.model.Weather;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface WeatherDAO {

    Integer insertWeatherList(List<Weather> weatherList);
}
