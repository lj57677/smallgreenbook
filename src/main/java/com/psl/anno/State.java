package com.psl.anno;


import com.psl.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StateValidation.class) //指定校验规则
public @interface State {
    //提供校验失败后的提示信息
    String message() default "{state只能为 '1(已发布)'' 或 '0(草稿)'}";

    //指定分组
    Class<?>[] groups() default {};

    //负载 获取到@State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
