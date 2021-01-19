package com.myself.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2021-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID，手机号
     */
    @TableId
    private long id;

    private String nickname;

    /**
     * MD5(MD5(pass明文+固定salt)+salt)
     */
    private String password;

    private String slat;

    /**
     * 头像
     */
    private String hand;

    /**
     * 注册时间
     */
    @TableField("register_date")
    private Date registerDate;

    /**
     * 最后一次登录时间
     */
    @TableField("last_login_date")
    private Date lastLoginDate;

    /**
     * 登录次数
     */
    @TableField("log_count")
    private Integer logCount;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
