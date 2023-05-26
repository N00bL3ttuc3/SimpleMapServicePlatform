package com.example.service.impl;

import com.example.dao.PosInfoDao;
import com.example.model.PosInfo;
import com.example.service.PosInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosInfoServiceImpl implements PosInfoService {
    @Autowired
    private PosInfoDao posInfoDao;

    @Override
    public int insertPosInfo(PosInfo posInfo) {
        return posInfoDao.insert(posInfo);
    }

    @Override
    public boolean deletePosInfo(double lat, double lng) {
        return posInfoDao.deleteByLatLng(lat,lng);
    }

    @Override
    public int updatePosInfo(PosInfo posInfo) {
        return posInfoDao.updateById(posInfo);
    }

    @Override
    public PosInfo getPosInfo(double lat, double lng) {
        return posInfoDao.selectByLatLng(lat,lng);
    }

    @Override
    public List getAllPosInfo() {
        return posInfoDao.selectList(null);
    }
}
