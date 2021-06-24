package com.wzp.nflj.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author wzp
 * @Date 2021/4/20 11:34
 **/
@ApiModel("通用model")
@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Model implements Serializable {
    private static final long serialVersionUID = 5753631120094066220L;

    @ApiModelProperty(value = "编号")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @CreatedDate
    private Long createdAt;

    @ApiModelProperty(value = "修改时间")
    @LastModifiedDate
    private Long updatedAt;


    public Model() {
    }

    public Model(Long id) {
        if (id != null) {
            this.id = id;
        }
    }
}
