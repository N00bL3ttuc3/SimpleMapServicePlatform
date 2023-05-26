package com.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.PosInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PosInfoDao extends BaseMapper<PosInfo> {
    PosInfo selectByLatLng(double lat, double lng);
    boolean deleteByLatLng(double lat,double lng);
}
