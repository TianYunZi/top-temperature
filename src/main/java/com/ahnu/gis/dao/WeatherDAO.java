package com.ahnu.gis.dao;

import com.ahnu.gis.model.Weather;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface WeatherDAO {

    Weather selectByPrimaryKey(Integer id);

    Integer insertWeatherList(List<Weather> weatherList);

    Integer updateWeatherReverseBatch(List<Weather> weatherList);
}
