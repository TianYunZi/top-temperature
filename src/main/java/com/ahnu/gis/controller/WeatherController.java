package com.ahnu.gis.controller;

import com.ahnu.gis.model.Weather;
import com.ahnu.gis.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.SynchronossPartHttpMessageReader;
import org.springframework.web.bind.annotation.*;
import org.synchronoss.cloud.nio.stream.storage.FileStreamStorage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;

/**
 * 天气Controller.
 */
@RestController
public class WeatherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/api/v1/weather/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Weather> getWeatherById(@PathVariable Integer id) {
        LOGGER.info("其实什么值都没有! 那是不可能的");
        return Mono.justOrEmpty(weatherService.selectByPrimaryKey(id));
    }

    @PostMapping("/api/v1/post/test")
    @ResponseStatus(HttpStatus.CREATED)
    public String postTest() {
        LOGGER.info("这是个post请求");
        return "这是个post请求";
    }

    @PostMapping("/api/v1/weather")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Integer> importWeather(@RequestPart("weather") FilePart weather) {
        LOGGER.info("开始传入数据");
        LOGGER.info("传入的文件名: {}", weather.filename());
        File file = new File("./upload/weather.xlsx");
        weather.transferTo(file);
        int result = weatherService.importExcel(file);
        LOGGER.info("结束传入数据");
        return Mono.justOrEmpty(result);
    }

    @PutMapping("/api/v1/weather/reverse")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Integer> updateWeatherReverseBatch() {
        LOGGER.info("开始更新备用字段");
        return Mono.justOrEmpty(weatherService.updateWeatherReverseBatch());
    }
}
