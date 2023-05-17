package com.baidu.novel.Controller.dto;

import com.baidu.novel.entity.Muen;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String username;
    private  String password;
    private String token;
    private String role;
    private int sate;
    private  String nickname;
    @TableField(exist = false)
    private List<Muen> muen;
//    private String avatarUrl;
}
