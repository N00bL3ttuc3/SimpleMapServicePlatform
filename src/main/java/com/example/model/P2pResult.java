package com.example.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class P2pResult {
    private Integer seq;
    private BigInteger gid;
    private String name;
    private Float cost;
    private Float azimuth;
    private String geom;
}
