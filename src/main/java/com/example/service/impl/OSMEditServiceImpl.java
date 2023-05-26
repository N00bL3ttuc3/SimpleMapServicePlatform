package com.example.service.impl;

import com.example.dao.OsmEditDao;
import com.example.model.OSMLineFearures;
import com.example.model.OSMPointFeatures;
import com.example.model.OSMPolygonFeatures;
import com.example.service.OSMEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class OSMEditServiceImpl implements OSMEditService {

    @Autowired
    private OsmEditDao osmEditDao;

    @Override
    public OSMPolygonFeatures getGeomFromLatLng(String point) {
        return osmEditDao.getGeomFromLatLng(point);
    }

    @Override
    public Boolean updatePolygons(String name,String aeroway,String amenity,String highway,String landuse,String leisure,String natural,String shop, String geom) {
        return osmEditDao.updatePolygons(name, aeroway, amenity, highway, landuse, leisure, natural, shop, geom);
    }

    @Override
    public Boolean insertPolygons(String name,String aeroway,String amenity,String highway,String landuse,String leisure,String natural,String shop, String geom) {
        return osmEditDao.insertPolygons(name, aeroway, amenity, highway, landuse, leisure, natural, shop, geom);
    }

    @Override
    public Boolean deletePolygons(String geom) {
        return osmEditDao.deletePolygons(geom);
    }

    @Override
    public OSMLineFearures getLineFromLatLng(String point) {
        return osmEditDao.getLineFromLatLng(point);
    }

    @Override
    public Boolean updateLine(String name, String highway, String railway, String way) {
        return osmEditDao.updateLine(name, highway, railway, way);
    }

    @Override
    public Boolean insertLine(String name, String highway, String railway, String way) {
        return osmEditDao.insertLine(name, highway, railway, way);
    }

    @Override
    public Boolean deleteLine(String way) {
        return osmEditDao.deleteLine(way);
    }

    @Override
    public OSMPointFeatures getPointFromLatLng(String point) {
        return osmEditDao.getPointFromLatLng(point);
    }

    @Override
    public Boolean updatePoint(String name, String amenity, String leisure, String shop, String way) {
        return osmEditDao.updatePoint(name, amenity, leisure, shop, way);
    }

    @Override
    public Boolean insertPoint(String name, String amenity, String leisure, String shop, String way) {
        return osmEditDao.insertPoint(name, amenity, leisure, shop, way);
    }

    @Override
    public Boolean deletePoint(String way) {
        return osmEditDao.deletePoint(way);
    }
}
