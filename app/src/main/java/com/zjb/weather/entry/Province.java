package com.zjb.weather.entry;

/**
 * Created by Administrator on 2016/10/25.
 */
public class Province {
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Province(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Province() {
    }
}
