package com.baidu.novel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("b_table")
public class Novel {
    @TableId(type = IdType.AUTO)
    Integer id;
    @TableField(value = "b_name")
    String bName;
    @TableField(value = "bookUrl")
    String bookUrl;
    String author;
    String brief;
    @TableField(value = "u_id")
    Integer uId;
    @TableField(value = "b_type")
    String bType;
    Integer heat;
    @TableField(exist = false)
    List<Chapters> chapters;
}
