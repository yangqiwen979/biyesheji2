package top.lothar.sdims.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description:
 */
public class EchartsDataBing {

    private String type;

    private Double total;

    private String days;


    List<String> legendData;

    List<Double> seriesData;

    List<Double[]> forecastList;

    public List<Double[]> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<Double[]> forecastList) {
        this.forecastList = forecastList;
    }

    public void setLegendData(List<String> legendData) {
        this.legendData = legendData;
    }

    public void setSeriesData(List<Double> seriesData) {
        this.seriesData = seriesData;
    }

    public List<Double> getSeriesData() {
        return seriesData;
    }

    public List<String> getLegendData() {
        return legendData;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public Double getTotal() {
        return total;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

/*
    public Date getDaysAsDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 假设days字段是"yyyy-MM-dd"格式
        return dateFormat.parse(days);
    }*/
}