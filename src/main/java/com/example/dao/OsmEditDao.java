package com.example.dao;

import com.example.model.OSMLineFearures;
import com.example.model.OSMPointFeatures;
import com.example.model.OSMPolygonFeatures;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OsmEditDao {
    OSMPolygonFeatures getGeomFromLatLng(String point);
    Boolean updatePolygons(String name,String aeroway,String amenity,String highway,String landuse,String leisure,String natural,String shop,String way);
    Boolean insertPolygons(String name,String aeroway,String amenity,String highway,String landuse,String leisure,String natural,String shop,String way);
    Boolean deletePolygons(String way);

    OSMLineFearures getLineFromLatLng(String point);
    Boolean updateLine(String name,String highway,String railway,String way);
    Boolean insertLine(String name,String highway,String railway,String way);
    Boolean deleteLine(String way);

    OSMPointFeatures getPointFromLatLng(String point);
    Boolean updatePoint(String name,String amenity,String leisure,String shop,String way);
    Boolean insertPoint(String name,String amenity,String leisure,String shop,String way);
    Boolean deletePoint(String way);
}
