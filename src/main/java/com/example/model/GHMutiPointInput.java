package com.example.model;

import com.graphhopper.util.shapes.GHPoint;
import lombok.Data;

import java.util.List;

@Data
public class GHMutiPointInput {
    private List<GHPoint> points;
}