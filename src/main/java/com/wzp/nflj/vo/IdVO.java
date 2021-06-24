package com.wzp.nflj.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zp.wei
 * @DATE: 2020/10/12 9:48
 */
@ApiModel("idVO")
@Data
public class IdVO implements Serializable {
    private static final long serialVersionUID = 188120544333332056L;

    @ApiModelProperty(value = "用户id", example = "1")
    private Long id;


}
