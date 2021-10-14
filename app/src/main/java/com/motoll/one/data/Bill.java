package com.motoll.one.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
    @DatabaseField(columnName = "time")
    private long time;
    public Bill() {
    }

    public Bill(long id, String type, String name, String sex, double price, long time) {
        Id = id;
        this.type = type;
        this.name = name;
        this.sex = sex;
        this.price = price;
        this.time = time;
    }

    public long getTime() {
        return time;
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
}
