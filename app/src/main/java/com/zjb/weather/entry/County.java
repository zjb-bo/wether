package com.zjb.weather.entry;

/**
 * Created by Administrator on 2016/10/25.
 */

public class County {
    private String id;
    private String name;
    private String city_id;

    public County() {
    }

    public County(String id, String name, String city_id) {
        this.id = id;
        this.name = name;
        this.city_id = city_id;
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

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
}
