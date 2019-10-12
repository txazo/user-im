package org.txazo.user.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Data
@Alias("account")
public class Account implements Serializable {

    private static final long serialVersionUID = 2203138981078972411L;

    private Integer userId;

    /**
     * 1-用户名
     * 2-邮箱
     * 3-手机号
     * 4-微信
     * 5-QQ
     * 6-微博
     */
    private Integer source;

    // 用户名、邮箱、手机号、第三方平台id
    private String account;

    // 密码、第三方平台access_token
    private String token;

    // 上一次登陆ip
    private String lastLoginIp;

    // 上一次登陆时间
    private Date lastLoginTime;

    private Date createTime;

    private Date updateTime;

}
