package com.myself.seckill.vo;

import cn.hutool.core.util.ReUtil;
import com.myself.seckill.validator.IsMobile;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/19 4:52 下午
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {


    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ReUtil.isMatch("/^1[345678]\\d{9}$/",value);

        }else {
            if (StringUtils.isEmpty(value)) {
                return  true;
            }else {
                return ReUtil.isMatch("/^1[345678]\\d{9}$/",value);
            }
        }
    }
}
