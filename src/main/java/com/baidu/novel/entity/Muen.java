package com.baidu.novel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("n_muen")
public class Muen {
    @TableId(type = IdType.AUTO)
    Integer id;
    String name;
    String path;
    String icon;
    String description;
    @TableField(exist = false)
    List<Muen> children;
    Integer pid;
    String pagepath;
}
