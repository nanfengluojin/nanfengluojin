package com.wzp.nflj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @Author: zp.wei
 * @DATE: 2021/3/31 1:54
 */
@ApiModel("用户")
@Setter
@Getter
@Entity
public class User extends BaseUser {
    private static final long serialVersionUID = -2942789610882477297L;

    @ApiModelProperty(value = "性别，0未知，1男，2女")
    private Integer sex = 0;


}
