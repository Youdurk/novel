package com.baidu.novel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 用户实体类
 */
@Data
@TableName("n_user")
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private Integer age;
    private String address;
    private String mobile;
    private String email;
    private String avatarurl;
    private String role;
    private String datatime;
    private int sate;
}
