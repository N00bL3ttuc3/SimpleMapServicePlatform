package com.example.model;

import lombok.Data;

@Data
public class OSMPolygonFeatures {
    private String way;//这个geom一定要封装成POINT(xxx xxx)、POLYGON(xxx xxx,xxx xxx...)形式
    private String name;
    private String aeroway;
    private String amenity;
    private String highway;
    private String landuse;
    private String leisure;
    private String natural;
    private String shop;

}
