package com.example.service;

import com.example.model.OSMLineFearures;
import com.example.model.OSMPointFeatures;
import com.example.model.OSMPolygonFeatures;

import java.util.Map;

public interface OSMEditService {
    OSMPolygonFeatures getGeomFromLatLng(String point);
    Boolean updatePolygons(String name,String aeroway,String amenity,String highway,String landuse,String leisure,String natural,String shop,String geom);
    Boolean insertPolygons(String name,String aeroway,String amenity,String highway,String landuse,String leisure,String natural,String shop,String geom);
    Boolean deletePolygons(String geom);

    OSMLineFearures getLineFromLatLng(String point);
    Boolean updateLine(String name,String highway,String railway,String way);
    Boolean insertLine(String name,String highway,String railway,String way);
    Boolean deleteLine(String way);

    OSMPointFeatures getPointFromLatLng(String point);
    Boolean updatePoint(String name,String amenity,String leisure,String shop,String way);
    Boolean insertPoint(String name,String amenity,String leisure,String shop,String way);
    Boolean deletePoint(String way);
}
