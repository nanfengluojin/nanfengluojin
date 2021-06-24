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
@ApiModel("角色")
public class Role extends Model {
    private static final long serialVersionUID = -1397604872839505965L;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    public Role() {
    }

    public Role(Long id) {
        super(id);
    }
}
