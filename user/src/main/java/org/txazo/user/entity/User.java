package org.txazo.user.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

@Data
@Alias("user")
public class User implements Serializable {

    private static final long serialVersionUID = -3165477234342871186L;

    private Integer id;

    // 用户id
    private Integer userId;

    // 昵称
    private String nickName;

    // 头像
    private String avatar;

    // 性别
    private Integer gender;

    // 出生日
    private Date birthday;

    private Date createTime;

    private Date updateTime;

}
