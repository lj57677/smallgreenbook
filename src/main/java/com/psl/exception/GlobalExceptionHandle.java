package com.psl.exception;

import com.psl.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(Exception.class) //指定异常类型
    public Result HandleException(Exception e){
        e.printStackTrace();
        //部分异常没有错误信息getMessage是空的，所以需要通过三元运算符进行判断，hasLength中为true则返回异常信息，为false则直接返回“操作失败”
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage() : "操作失败");
    }
}
