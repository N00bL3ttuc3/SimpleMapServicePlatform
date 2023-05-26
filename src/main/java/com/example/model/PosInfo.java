package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName(value = "marked_places")
public class PosInfo {
    @TableId( type = IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    private Double lat;
    private Double lng;
    private String tag;
    private String description;
}
