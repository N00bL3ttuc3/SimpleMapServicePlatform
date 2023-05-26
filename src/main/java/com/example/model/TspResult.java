package com.example.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TspResult {
    private Integer seq;
    private BigInteger gid;
    private String name;
    private Double cost;
    private Double azimuth;
    private String readable;
    private String geom;
}
