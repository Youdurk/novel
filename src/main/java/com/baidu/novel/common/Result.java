package com.baidu.novel.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor//无参构造
@AllArgsConstructor //有参构造
public class Result {
    private String code;
    private String msg;
    private Object data;



 public static Result success(){return new Result(Constant.CODE_200,"",null);}
 public static Result success(Object data){return new Result(Constant.CODE_200,"",data);}
 public static Result errorr(){return new Result(Constant.CODE_500,"系统错误",null);}
 public static Result errorr(String code,String msg){return new Result(code,msg,null);}
}
