package com.wzp.nflj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author: zp.wei
 * @DATE: 2021/3/31 10:54
 */
@Entity
@Getter
@Setter
@ApiModel("三级省市级联")
public class Areas implements Serializable {

    private static final long serialVersionUID = -2071059585293261007L;

    @ApiModelProperty(value = "编号")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "父ID")
    private Integer parentId;

    @ApiModelProperty(value = "级别")
    private Integer levelType;

    @ApiModelProperty(value = "全称")
    private String name;

    @ApiModelProperty(value = "简称")
    private String shortName;

    @ApiModelProperty(value = "区号")
    private String cityCode;


}
