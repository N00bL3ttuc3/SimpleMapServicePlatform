package com.example.service.impl;

import com.example.dao.CityRoutingDao;
import com.example.service.CityRoutingService;
import org.geolatte.geom.ByteBuffer;
import org.geolatte.geom.Geometry;
import org.geolatte.geom.Position;
import org.geolatte.geom.codec.Wkb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityRoutingServiceImpl implements CityRoutingService {

    @Autowired
    private CityRoutingDao cityRoutingDao;

    @Override
    public List getP2pPlanPath(String edges_subset, Object x1, Object y1, Object x2, Object y2) {
        return cityRoutingDao.p2pPlan(edges_subset, x1, y1, x2, y2);
    }

    @Override
    public Integer getTopologyNumber(double x, double y) {
        return cityRoutingDao.xy2topology(x, y);
    }

    @Override
    public List getTspPlanPath(String edges_subset, Integer[] source, int start_id) {
        return cityRoutingDao.tspPlan(edges_subset, source, start_id);
    }

    @Override
    public String getTransformPos(String pos,Integer sourceSrid,Integer targetSrid) {
        return cityRoutingDao.posTransform(pos,sourceSrid,targetSrid);
    }


    @Override
    public List transOnePos(double x, double y, Integer sourceSrid, Integer targetSrid) {
        ArrayList res = new ArrayList();
        String geom = "POINT(" + x +" " + y +")";
        String geomTrans = cityRoutingDao.posTransform(geom, sourceSrid, targetSrid);
        Geometry geo = Wkb.fromWkb(ByteBuffer.from(geomTrans));
        Position position = geo.getPositionN(0);
        res.add(position.getCoordinate(0));
        res.add(position.getCoordinate(1));
        return res;
    }
}
