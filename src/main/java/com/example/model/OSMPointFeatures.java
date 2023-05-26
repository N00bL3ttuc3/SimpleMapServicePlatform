package com.example.model;


import lombok.Data;

@Data
public class OSMPointFeatures {
    private String way;
    private String name;
    private String amenity;
    private String leisure;
    private String shop;
}
