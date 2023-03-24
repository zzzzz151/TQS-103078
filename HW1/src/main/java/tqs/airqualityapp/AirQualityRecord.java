package tqs.airqualityapp;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AirQualityRecord {
    private String city;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    private double co, no2, o3, so2, pm2_5, pm10;

    public AirQualityRecord(String city, LocalDate date)
    {
        this.city = city;
        this.date = date;
        this.co = -1;
        this.no2 = -1;
        this.o3 = -1;
        this.so2 = -1;
        this.pm2_5 = -1;
        this.pm10 = -1;
    }

    public AirQualityRecord() {
        this.city = null;
        this.date = null;
        this.co = -1;
        this.no2 = -1;
        this.o3 = -1;
        this.so2 = -1;
        this.pm2_5 = -1;
        this.pm10 = -1;
    }

    @Override
    public String toString() {
        return "AirQualityRecord [city=" + city + ", date=" + date + ", co=" + co + ", no2=" + no2 + ", o3=" + o3
                + ", so2=" + so2 + ", pm2_5=" + pm2_5 + ", pm10=" + pm10 + "]";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCo() {
        return co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getNo2() {
        return no2;
    }

    public void setNo2(double no2) {
        this.no2 = no2;
    }

    public double getO3() {
        return o3;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    public double getSo2() {
        return so2;
    }

    public void setSo2(double so2) {
        this.so2 = so2;
    }

    public double getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(double pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public double getPm10() {
        return pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

}
