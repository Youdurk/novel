package com.baidu.novel.exception;

import com.baidu.novel.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 如果抛出的异常是ServiceException，则调用改方法
     * @param se
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
   public Result handle(ServiceException se){return Result.errorr(se.getCode(),se.getMessage());}
}
