package com.wzp.nflj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @Author wzp
 * @Date 2021/4/20 11:34
 **/
@Entity
@Getter
@Setter
@ApiModel("权限")
public class Authority extends Model {
    private static final long serialVersionUID = -5894192840289554744L;

    public static final Long collection = 501L;

    public static final Long Service_transfer = 502L;

    public static final Long Service_transfer_special = 503L;


    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("值")
    private String val;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("权限码")
    private Long idCode;

}