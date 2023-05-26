package com.example.service;

import com.example.model.PosInfo;

import java.util.List;

public interface PosInfoService {
    int insertPosInfo(PosInfo posInfo);
    boolean deletePosInfo(double lat,double lng);
    int updatePosInfo(PosInfo posInfo);
    PosInfo getPosInfo(double lat, double lng);
    List getAllPosInfo();
}
