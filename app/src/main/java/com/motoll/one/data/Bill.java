package com.motoll.one.data;

import android.annotation.SuppressLint;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xuexiang.xutil.data.DateUtils;

import java.text.SimpleDateFormat;

@DatabaseTable(tableName = "bill")
public class Bill {
    @DatabaseField(generatedId = true)
    private long Id;
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "sex")
    private String sex;
    @DatabaseField(columnName = "price")
    private double price;
    @DatabaseField(columnName = "date")
    private String date;
    @DatabaseField(columnName = "year")
    private String year;
    @DatabaseField(columnName = "year_month")
    private String year_month;
    @DatabaseField(columnName = "time")
    private long time;
    public Bill() {
    }

    public Bill(long id, String type, String name, String sex, double price, String date, String year, String year_month) {
        Id = id;
        this.type = type;
        this.name = name;
        this.sex = sex;
        this.price = price;
        this.date = date;
        this.year = year;
        this.year_month = year_month;
    }

    public long getTime() {
        return time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear_month() {
        return year_month;
    }

    public void setYear_month(String year_month) {
        this.year_month = year_month;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public void setDate(String date) {
        time=DateUtils.string2Millis(date,new SimpleDateFormat("yyyy-MM-dd"));
        this.date = date;
    }
}
