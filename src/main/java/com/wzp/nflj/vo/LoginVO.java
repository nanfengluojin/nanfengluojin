package com.wzp.nflj.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/28 16:57
 */
@ApiModel("登录VO")
@Data
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 10300087461480759L;

    @ApiModelProperty(value = "用户名", example = "superAdmin", required = true)
    private String username;

    @ApiModelProperty(value = "密码", example = "72d0f0fea337e605417044de8c749982", required = true)
    private String password;

}
