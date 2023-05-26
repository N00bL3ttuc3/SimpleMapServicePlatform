package com.example.controller;


import com.example.model.*;
import com.example.service.CityRoutingService;
import org.geolatte.geom.ByteBuffer;
import org.geolatte.geom.Geometry;
import org.geolatte.geom.Position;
import org.geolatte.geom.codec.Wkb;
import org.geolatte.geom.codec.Wkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CityRoutingController {

    @Autowired
    private CityRoutingService cityRoutingService;

    @PostMapping("/p2p")
    public List p2pPath(@RequestBody P2pInput p2pInput){
        String startGeom = "POINT(" + p2pInput.getY1() +" " + p2pInput.getX1() +")";
        String targetGeom = "POINT(" + p2pInput.getY2() +" " + p2pInput.getX2() +")";
        System.out.println("startGeom:"+startGeom+" targetGeom"+targetGeom);
        String startGeomTrans = cityRoutingService.getTransformPos(startGeom, 4326, 3857);
        String targetGeomTrans = cityRoutingService.getTransformPos(targetGeom, 4326, 3857);
        Geometry startGeo = Wkb.fromWkb(ByteBuffer.from(startGeomTrans));
        Geometry targetGeo = Wkb.fromWkb(ByteBuffer.from(targetGeomTrans));
        Position startPosition = startGeo.getPositionN(0);
        Position targetPosition = targetGeo.getPositionN(0);
        System.out.println("-------------------");
        System.out.println("x1:"+startPosition.getCoordinate(0)+"y1:"+startPosition.getCoordinate(1));
        System.out.println("x1:"+targetPosition.getCoordinate(0)+"y1:"+targetPosition.getCoordinate(1));
        return cityRoutingService.getP2pPlanPath(p2pInput.getEdges_subset(),
                startPosition.getCoordinate(0),startPosition.getCoordinate(1),
                targetPosition.getCoordinate(0),targetPosition.getCoordinate(1));
    }
    

    @PostMapping("/newP2p")
    public List p2pCoordinates(@RequestBody P2pInput p2pInput){
        ArrayList startPoint = (ArrayList) cityRoutingService.transOnePos(p2pInput.getX1(),p2pInput.getY1(),4326,3857);
        ArrayList targetPoint = (ArrayList) cityRoutingService.transOnePos(p2pInput.getX2(),p2pInput.getY2(),4326,3857);
        System.out.println("startPoint:"+startPoint.toString());
        System.out.println("targetPoint:"+targetPoint.toString());
        List path = cityRoutingService.getP2pPlanPath(p2pInput.getEdges_subset(),
                startPoint.get(0),startPoint.get(1),targetPoint.get(0),targetPoint.get(1));
        List coordinates = new ArrayList();
        for (int i=0;i<path.size();i++){
            P2pResult p2pPathResult = (P2pResult) path.get(i);
            Geometry geo = Wkb.fromWkb(ByteBuffer.from(p2pPathResult.getGeom()));
            String pathInfo = Wkt.toWkt(geo).split(";")[1];
            System.out.println(pathInfo);
            String geom = cityRoutingService.getTransformPos(pathInfo,3857,4326);
            Geometry geo2 = Wkb.fromWkb(ByteBuffer.from(geom));
            for (int j=0;j<geo2.getNumPositions();j++){
                Position positionN = geo2.getPositionN(j);
                coordinates.add(new double[]{positionN.getCoordinate(0),positionN.getCoordinate(1)});
                }
            }
        return coordinates;
    }


    @PostMapping("/tsp")
    public List tspPath(@RequestBody TspInput tspInput){
        int start_id = cityRoutingService.getTopologyNumber(tspInput.getStart_x(),tspInput.getStart_y());
        int len = tspInput.getX_list().length;
        Integer[] points = new Integer[len];
        for (int i=0;i<len;i++){
            points[i] = cityRoutingService.getTopologyNumber(tspInput.getX_list()[i],tspInput.getY_list()[i]);
        }
        List path = cityRoutingService.getTspPlanPath(tspInput.getEdges_subset(), points, start_id);
        return path;
    }


    @PostMapping("/newTsp")
    public List tspCoordinates(@RequestBody TspInput tspInput){
        ArrayList startPoint = (ArrayList) cityRoutingService.transOnePos(tspInput.getStart_x(),tspInput.getStart_y(),4326,3857);
        Double[] X_List = tspInput.getX_list();
        Double[] Y_List = tspInput.getY_list();
        ArrayList<ArrayList<Double>> targetPoints = new ArrayList();
        for (int i=0;i<X_List.length;i++){
            ArrayList tmpPoint = (ArrayList) cityRoutingService.transOnePos(X_List[i],Y_List[i],4326,3857);
            targetPoints.add(tmpPoint);
        }
        int start_id = cityRoutingService.getTopologyNumber((Double)startPoint.get(0),(Double)startPoint.get(1));
        int len = targetPoints.size();
        Integer[] points = new Integer[len];
        for (int i=0;i<len;i++){
            points[i] = cityRoutingService.getTopologyNumber(targetPoints.get(i).get(0),targetPoints.get(i).get(1));
        }
        List path = cityRoutingService.getTspPlanPath(tspInput.getEdges_subset(), points, start_id);
        List coordinates = new ArrayList();
        for (int i=0;i<path.size();i++){
            TspResult tspPathResult = (TspResult) path.get(i);
            String pathInfo = tspPathResult.getReadable();
            String geom = cityRoutingService.getTransformPos(pathInfo,3857,4326);
            Geometry geo = Wkb.fromWkb(ByteBuffer.from(geom));
            for (int j=0;j<geo.getNumPositions();j++){
                Position positionN = geo.getPositionN(j);
                coordinates.add(new double[]{positionN.getCoordinate(0),positionN.getCoordinate(1)});
            }
        }
        return coordinates;
    }
}