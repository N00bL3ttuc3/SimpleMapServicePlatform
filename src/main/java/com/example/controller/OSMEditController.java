package com.example.controller;

import com.example.model.OSMLineFearures;
import com.example.model.OSMPointFeatures;
import com.example.model.OSMPolygonFeatures;
import com.example.service.OSMEditService;
import jdk.jfr.ContentType;
import org.geolatte.geom.codec.db.sqlserver.OpenGisType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class OSMEditController {

    @Autowired
    private OSMEditService osmEditService;

    @PostMapping("/cleanCache")
    private Boolean cleanCache() throws IOException {
        System.out.println("no");
        try {
            Socket socket = new Socket("192.168.65.8",8088);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().exec("/Users/bianzhenkun/spring_study/graductionWork/src/main/java/com/example/scripts/RmCache.sh");
        System.out.println("yes");
        return true;
    }

    @PostMapping("/test")
    public Boolean test(){
        return true;
    }

    @GetMapping("/polygonEdit/{lng}/{lat}")
    public OSMPolygonFeatures getPolygonInfo(@PathVariable double lng,@PathVariable double lat){
        String geom = "POINT(" + lng + " " + lat + ")";
        return osmEditService.getGeomFromLatLng(geom);
    }

    @PostMapping("/polygonEdit")
    public Boolean updatePolygon(@RequestBody OSMPolygonFeatures input) {
        return osmEditService.updatePolygons(input.getName(),input.getAeroway(),input.getAmenity(),input.getHighway(),input.getLanduse(),input.getLeisure(),input.getNatural(),input.getShop(),input.getWay());
    }

    @PutMapping("/polygonEdit")
    public Boolean insertPolygon(@RequestBody OSMPolygonFeatures input){
        return osmEditService.insertPolygons(input.getName(),input.getAeroway(),input.getAmenity(),input.getHighway(),input.getLanduse(),input.getLeisure(),input.getNatural(),input.getShop(),input.getWay());
    }

    @DeleteMapping("/polygonEdit")
    public Boolean deletePolygon(@RequestBody OSMPolygonFeatures input){
        return osmEditService.deletePolygons(input.getWay());
    }



    @GetMapping("/lineEdit/{lng}/{lat}")
    public OSMLineFearures getLineInfo(@PathVariable double lng, @PathVariable double lat){
        String geom = "POINT(" + lng + " " + lat + ")";
        return osmEditService.getLineFromLatLng(geom);
    }

    @PostMapping("/lineEdit")
    public Boolean updateLine(@RequestBody OSMLineFearures input) {
        return osmEditService.updateLine(input.getName(), input.getHighway(), input.getRailway(), input.getWay());
    }

    @PutMapping("/lineEdit")
    public Boolean insertLine(@RequestBody OSMLineFearures input){
        System.out.println(input.toString());
        return osmEditService.insertLine(input.getName(),input.getHighway(),input.getRailway(),input.getWay());
    }

    @DeleteMapping("/lineEdit")
    public Boolean deleteLine(@RequestBody OSMLineFearures input){
        return osmEditService.deleteLine(input.getWay());
    }


    @GetMapping("/pointEdit/{lng}/{lat}")
    public OSMPointFeatures getPointInfo(@PathVariable double lng, @PathVariable double lat){
        String geom = "POINT(" + lng + " " + lat + ")";
        return osmEditService.getPointFromLatLng(geom);
    }

    @PostMapping("/pointEdit")
    public Boolean updatePoint(@RequestBody OSMPointFeatures input) {
        return osmEditService.updatePoint(input.getName(), input.getAmenity(),input.getLeisure(),input.getShop(),input.getWay());
    }

    @PutMapping("/pointEdit")
    public Boolean insertPoint(@RequestBody OSMPointFeatures input){
        System.out.println(input.toString());
        return osmEditService.insertPoint(input.getName(),input.getAmenity(),input.getLeisure(),input.getShop(),input.getWay());
    }

    @DeleteMapping("/pointEdit")
    public Boolean deletePoint(@RequestBody OSMPointFeatures input){
        return osmEditService.deletePoint(input.getWay());
    }

}
