package com.wzp.nflj.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/13 13:48
 */
@ApiModel("修改密码VO")
@Data
public class UpdatePasswordVO implements Serializable {
    private static final long serialVersionUID = 1881205447443932033L;

    @ApiModelProperty(value = "用户id", example = "1")
    private Long id;

    @ApiModelProperty("修改时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "旧密码", example = "2w2d1wr23423422")
    private String oldPassword;

    @ApiModelProperty(value = "新密码", example = "2w2d2w3ewr23423422")
    private String newPassword;


}
