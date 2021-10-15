package com.motoll.one.data;

import android.annotation.SuppressLint;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.xuexiang.xutil.data.DateUtils;

import java.text.SimpleDateFormat;

@DatabaseTable(tableName = "bill")
public class Bill {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(columnName = "name")
    private String name;
    //账户id
    @DatabaseField(columnName = "account_id")
    private long account_id;
    //账户名称 eg:交通银行信用卡
    @DatabaseField(columnName = "account_name")
    private String account_name;
    //购买成员 eg:张三|李四 |间隔名称不能含有|符号
    @DatabaseField(columnName = "members")
    private String members;
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

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        time= DateUtils.string2Millis(date,new SimpleDateFormat("yyyy-MM-dd"));
        this.date = date;
    }
}
