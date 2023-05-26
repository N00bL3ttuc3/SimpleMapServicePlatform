package com.example.controller;


import com.example.model.GHMutiPointInput;
import com.example.model.GHP2pInput;
import com.example.service.GHEngineRoutingService;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.util.PointList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class GHEngineRoutingController {

    @Autowired
    private GHEngineRoutingService ghEngineRoutingService;

    /*
    不再使用本方法
     */
    @PostMapping("/oldGHp2p")
    public List getP2pPath(@RequestBody GHP2pInput input){
        GraphHopper hopper = ghEngineRoutingService.createGraphHopperInstance();
        return ghEngineRoutingService.getP2pPointList(hopper,input.getFromLat(),input.getFromLon(),input.getToLat(),input.getToLon());
    }

    /*
    不再使用本方法
     */
    @PostMapping("/oldGHp2pInfo")
    public List getP2pPathInstructions(@RequestBody GHP2pInput input){
        GraphHopper hopper = ghEngineRoutingService.createGraphHopperInstance();
        return ghEngineRoutingService.getP2pPathInstructions(hopper,input.getFromLat(),input.getFromLon(),input.getToLat(),input.getToLon());
    }

    @PostMapping("/GHp2p")
    public List<Object> getP2pAllInfo(@RequestBody GHP2pInput input){
        GraphHopper hopper = ghEngineRoutingService.createGraphHopperInstance();
        PathWrapper allInfo = ghEngineRoutingService.getP2pAllInfo(hopper, input.getFromLat(), input.getFromLon(), input.getToLat(), input.getToLon());
        ArrayList<Object> result = new ArrayList();
        //封装总路径长度信息
        result.add(allInfo.getDistance());
        //封装经纬度信息
        PointList points = allInfo.getPoints();
        ArrayList<double[]> pointsLatLng = new ArrayList<>();
        for (int i=0;i<points.size();i++){
            pointsLatLng.add(new double[]{points.getLon(i),points.getLat(i)});
        }
        result.add(pointsLatLng);
        //封装指令信息
        List<Map<String, Object>> instructions = allInfo.getInstructions().createJson();
        result.add(instructions);
        return result;
    }

    /*
        坑：post的时候GHPoint有一个"valid": true属性
     */
    @PostMapping("/GHMutiP2p")
    public List<Object> getMutiP2pAllInfo(@RequestBody GHMutiPointInput input){
        GraphHopper hopper = ghEngineRoutingService.createGraphHopperInstance();
        PathWrapper allInfo = ghEngineRoutingService.getMutiP2pAllInfo(hopper, input.getPoints());
        ArrayList<Object> result = new ArrayList();
        //封装总路径长度信息
        result.add(allInfo.getDistance());
        //封装经纬度信息
        PointList points = allInfo.getPoints();
        ArrayList<double[]> pointsLatLng = new ArrayList<>();
        for (int i=0;i<points.size();i++){
            pointsLatLng.add(new double[]{points.getLon(i),points.getLat(i)});
        }
        result.add(pointsLatLng);
        //封装指令信息
        List<Map<String, Object>> instructions = allInfo.getInstructions().createJson();
        result.add(instructions);
        return result;
    }




    @PostMapping("/GHTsp")
    public List<Object> getTspAllInfo(@RequestBody GHMutiPointInput input){
        GraphHopper hopper = ghEngineRoutingService.createGraphHopperInstance();
        PathWrapper allInfo = ghEngineRoutingService.getTspAllInfo(hopper,input.getPoints());
        ArrayList<Object> result = new ArrayList();
        //封装总路径长度信息
        result.add(allInfo.getDistance());
        //封装经纬度信息
        PointList points = allInfo.getPoints();
        ArrayList<double[]> pointsLatLng = new ArrayList<>();
        for (int i=0;i<points.size();i++){
            pointsLatLng.add(new double[]{points.getLon(i),points.getLat(i)});
        }
        result.add(pointsLatLng);
        //封装指令信息
        List<Map<String, Object>> instructions = allInfo.getInstructions().createJson();
        result.add(instructions);
        return result;
    }
}