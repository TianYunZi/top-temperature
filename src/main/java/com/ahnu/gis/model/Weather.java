package com.ahnu.gis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 天气
 */
public class Weather {

    /**
     * 主键(自增)
     */
    private Long id;

    /**
     * 日期(yyyyMMdd)
     */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    /**
     * 风速
     */
    private BigDecimal speed;

    /**
     * 温度
     */
    private BigDecimal temperature;

    /**
     * 露点温度
     */
    private BigDecimal dewPointTemperature;

    /**
     * 降雨量
     */
    private BigDecimal rainFall;

    /**
     * 备注字段1
     */
    private String reverseFirst;

    /**
     * 备注字段1
     */
    private String reverseSecond;

    /**
     * 相对湿度
     */
    private BigDecimal relativeHumidity;

    public Weather() {
    }

    public Weather(Long id, String reverseFirst, String reverseSecond) {
        this.id = id;
        this.reverseFirst = reverseFirst;
        this.reverseSecond = reverseSecond;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getDewPointTemperature() {
        return dewPointTemperature;
    }

    public void setDewPointTemperature(BigDecimal dewPointTemperature) {
        this.dewPointTemperature = dewPointTemperature;
    }

    public BigDecimal getRainFall() {
        return rainFall;
    }

    public void setRainFall(BigDecimal rainFall) {
        this.rainFall = rainFall;
    }

    public BigDecimal getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(BigDecimal relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public String getReverseFirst() {
        return reverseFirst;
    }

    public void setReverseFirst(String reverseFirst) {
        this.reverseFirst = reverseFirst;
    }

    public String getReverseSecond() {
        return reverseSecond;
    }

    public void setReverseSecond(String reverseSecond) {
        this.reverseSecond = reverseSecond;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", date=" + date +
                ", speed=" + speed +
                ", temperature=" + temperature +
                ", dewPointTemperature=" + dewPointTemperature +
                ", rainFall=" + rainFall +
                ", reverseFirst='" + reverseFirst + '\'' +
                ", reverseSecond='" + reverseSecond + '\'' +
                ", relativeHumidity=" + relativeHumidity +
                '}';
    }
}
