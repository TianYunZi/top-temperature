package com.ahnu.gis.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理.
 */
@RestControllerAdvice
public class WeatherControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherControllerAdvice.class);

    /**
     * 处理{@link WeatherException}
     *
     * @param exception
     * @param
     * @return
     */
    @ExceptionHandler(value = WeatherException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Map<String, Object>> errorWeatherHandler(WeatherException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorCode", exception.getErrorCode());
        map.put("errorMessage", exception.getErrorMessage());
        LOGGER.info("异常代码: {}, 异常原因: {}", new Object[]{exception.getErrorCode(), exception});
        return Mono.justOrEmpty(map);
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Map<String, Object>> errorNullPointerExceptionHandler(NullPointerException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorCode", "RE000001");
        map.put("errorMessage", exception.getMessage());
        LOGGER.info("异常代码: {}, 异常原因: {}", new Object[]{"RE000001", exception});
        return Mono.justOrEmpty(map);
    }

    @ExceptionHandler(value = ArithmeticException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Map<String, Object>> errorArithmeticExceptionHandler(ArithmeticException exception) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorCode", "RE000002");
        map.put("errorMessage", exception.getMessage());
        LOGGER.info("异常代码: {}, 异常原因: {}", new Object[]{"RE000002", exception});
        return Mono.justOrEmpty(map);
    }
}
