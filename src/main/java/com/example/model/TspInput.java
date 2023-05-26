package com.example.model;

import lombok.Data;

@Data
public class TspInput {
    private String edges_subset;
    private Double[] x_list;
    private Double[] y_list;
    private Double start_x;
    private Double start_y;
}
