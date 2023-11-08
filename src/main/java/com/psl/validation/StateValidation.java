package com.psl.validation;

import com.psl.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 *
 * @Param value 要校验的参数值
 * @Param context
 *
 * @return 如果返回false,则校验不通过,如果返回true,则校验通过
 */
public class StateValidation implements ConstraintValidator<State,Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null){
            return false;
        }
        if (value == 1 || value == 0){
            return true;
        }
        return false;
    }
}
