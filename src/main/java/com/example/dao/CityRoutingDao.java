package com.example.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityRoutingDao {
    Integer xy2topology(double x,double y);
    List tspPlan(String edges_subset, Integer[] source, int start_id);
    List p2pPlan(String edges_subset, Object x1, Object y1, Object x2, Object y2);
    String posTransform(String inputGeom,Integer sourceSrid,Integer targetSrid);
}
