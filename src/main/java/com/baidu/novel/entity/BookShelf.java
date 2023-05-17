package com.baidu.novel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 书架实体
 */
@Data
@TableName("bookshelf")
public class BookShelf {
    Integer userid;
    Integer tableid;
}
