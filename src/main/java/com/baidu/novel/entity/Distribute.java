package com.baidu.novel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("distribute")
public class Distribute {
    Integer roleid;
    Integer muenid;
}
