package com.ahnu.gis.model;

import java.math.BigDecimal;
import java.time.LocalDate;

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
     * 相对湿度
     */
    private BigDecimal relativeHumidity;

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

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", date=" + date +
                ", speed=" + speed +
                ", temperature=" + temperature +
                ", dewPointTemperature=" + dewPointTemperature +
                ", rainFall=" + rainFall +
                ", relativeHumidity=" + relativeHumidity +
                '}';
    }
}
