package com.zjb.weather.entry;

/**
 * Created by Administrator on 2016/10/25.
 */

public class City {
    private String id;
    private String name;
    private String province_id;

    public City() {
    }

    public City(String id, String name, String province_id) {
        this.id = id;
        this.name = name;
        this.province_id = province_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getprovince_id() {
        return province_id;
    }

    public void setprovince_id(String province_id) {
        this.province_id = province_id;
    }
}
