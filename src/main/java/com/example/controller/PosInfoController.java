package com.example.controller;

import com.example.model.PosInfo;
import com.example.service.PosInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PosInfoController {

    @Autowired
    private PosInfoService posInfoService;

    @GetMapping("/posInfo/{lat}&{lng}")
    public PosInfo getPosInfo(@PathVariable double lat, @PathVariable double lng ){
        return posInfoService.getPosInfo(lat,lng);
    }

    @PutMapping("/posInfo")
    public int insertPosInfo(@RequestBody PosInfo posInfo){
        return posInfoService.insertPosInfo(posInfo);
    }

    @DeleteMapping("/posInfo/{lat}&{lng}")
    public boolean deletePosInfo(@PathVariable double lat,@PathVariable double lng){
        return posInfoService.deletePosInfo(lat,lng);
    }

    @PostMapping("/posInfo")
    public int updatePosInfo(@RequestBody PosInfo posInfo){
        return posInfoService.updatePosInfo(posInfo);
    }

    @GetMapping("/posInfo")
    public List getAllPosInfo(){
        return posInfoService.getAllPosInfo();
    }

}
