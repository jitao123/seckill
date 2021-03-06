package com.myself.seckill.vo;

import com.myself.seckill.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/19 3:13 下午
 */
@Data
public class LoginVo {

    @NotNull(message = "手机号不能为空")
    @IsMobile
    private String mobile;

    @NotNull(message = "密码不能为空")
    @Length(min = 32,message = "密码最少是32位")
    private String password;
}
