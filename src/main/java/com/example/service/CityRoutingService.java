package com.example.service;

import java.util.List;


public interface CityRoutingService {
    List getP2pPlanPath(String edges_subset, Object x1, Object y1, Object x2, Object y2);
    Integer getTopologyNumber(double x,double y);
    List getTspPlanPath(String edges_subset, Integer[] source, int start_id);
    String getTransformPos(String pos,Integer sourceSrid,Integer targetSrid);
    List transOnePos(double x,double y,Integer sourceSrid,Integer targetSrid);
}
