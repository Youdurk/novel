package com.baidu.novel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("b_chapters")
public class Chapters {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(exist = false)
    private  String bName;
    private  String  chapters;
    private String content;
    private  Integer sequence;
    private Integer BId;

    public int compareTo(Chapters chapters) {
        return this.sequence-chapters.sequence;
    }
}
