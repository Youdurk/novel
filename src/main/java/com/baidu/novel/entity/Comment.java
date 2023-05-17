package com.baidu.novel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String content;
    private Integer tableid;
    private Integer userid;
    @TableField(exist = false)
    private String bName;
    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String avatarurl;
}
