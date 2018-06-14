package com.ahnu.gis.service;

import com.ahnu.gis.dao.WeatherDAO;
import com.ahnu.gis.exceptions.WeatherException;
import com.ahnu.gis.model.Weather;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private WeatherDAO weatherDAO;

    @Override
    public int importExcel(File file) {
        LOGGER.info("开始读取excel");
        Workbook wb = readExcel(file);
        LOGGER.info("读取excel完成");
        List<Weather> weatherList = new ArrayList<>();
        String[] columeNames = new String[0];
        if (wb != null) {
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                // 获取sheet
                Sheet sheet = wb.getSheetAt(i);
                if ("Sheet1".equals(sheet.getSheetName()) || "Sheet3".equals(sheet.getSheetName())) {
                    for (int intRow = 0; intRow < sheet.getPhysicalNumberOfRows(); intRow++) {
                        if (intRow == 0) {
                            Row row = sheet.getRow(intRow);
                            //获取最大列数
                            int colnumMaxNum = row.getPhysicalNumberOfCells();
                            columeNames = new String[colnumMaxNum];
                            for (int columeNum = 0; columeNum < colnumMaxNum; columeNum++) {
                                Cell cell = row.getCell(columeNum);
                                String cellData = (String) getCellFormatValue(cell);
                                if (StringUtils.isBlank(cellData)) {
                                    LOGGER.info("列数据为空，跳出循环");
                                    break;
                                }
                                columeNames[columeNum] = cellData;
                            }
                            //LOGGER.info("列名集合: {}", columeNames);
                        } else {
                            Row row = sheet.getRow(intRow);
                            if (StringUtils.isBlank((String) getCellFormatValue(row.getCell(0)))) {
                                LOGGER.info("行数据为空,跳出循环");
                                break;
                            }
                            if (columeNames.length == 0) {
                                LOGGER.info("没有获得列名");
                                throw new WeatherException("WE000002", "没有获得列名");
                            }
                            Weather weather = new Weather();
                            for (int columeNum = 0; columeNum < row.getPhysicalNumberOfCells(); columeNum++) {
                                Cell cell = row.getCell(columeNum);
                                String cellData = (String) getCellFormatValue(cell);
                                if (StringUtils.isBlank(cellData)) {
                                    LOGGER.info("列数据为空，跳出循环");
                                    break;
                                }
                                switch (columeNames[columeNum]) {
                                    case "日期":
                                        // LOGGER.info("行号: {}, 数据: {}", intRow, cellData);
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                                        String str = (String) getCellFormatValue(cell);
                                        String[] splits = str.split("E");
                                        str = splits[0];
                                        //LOGGER.info("str: {}", str);
                                        BigDecimal decimalCellData = new BigDecimal(str).multiply(new BigDecimal(10000000));
                                        Long longCellData = decimalCellData.longValue();
                                        //LOGGER.info("longCellData: {}", longCellData);
                                        cellData = String.valueOf(longCellData);
                                        LocalDate localDate = LocalDate.parse(cellData, formatter);
                                        weather.setDate(localDate);
                                        break;
                                    case "风速":
                                        weather.setSpeed(new BigDecimal(cellData));
                                        break;
                                    case "温度":
                                        weather.setTemperature(new BigDecimal(cellData));
                                        break;
                                    case "露点温度":
                                        weather.setDewPointTemperature(new BigDecimal(cellData));
                                        break;
                                    case "降雨量":
                                        weather.setRainFall(new BigDecimal(cellData));
                                        break;
                                    case "相对湿度":
                                        weather.setRelativeHumidity(new BigDecimal(cellData));
                                        break;
                                    default:
                                        break;
                                }
                            }
                            weatherList.add(weather);
                        }
                    }
                }
                LOGGER.info("第一个Sheet读取完成");
            }
        }
        LOGGER.info("读取weatherList的总大小: {}, 第一行数据: {}", weatherList.size(), weatherList.get(0));
        return weatherDAO.insertWeatherList(weatherList);
    }

    /**
     * 读取excel
     *
     * @param file
     * @return
     */
    private Workbook readExcel(File file) {
        Workbook wb = null;
        if (file == null) {
            LOGGER.info("传入的文件为空");
            throw new WeatherException("FN000001", "传入的文件为空");
        }
        String filePath = file.getPath();
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wb;
    }

    private Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellTypeEnum()) {
                case NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case FORMULA: {
                    //判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }
}
