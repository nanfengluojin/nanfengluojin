package com.wzp.nflj.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 管理员角色关系表
 */
@Entity
@Getter
@Setter
public class AdminRole implements Serializable {

    private static final long serialVersionUID = 5186818311630146999L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Admin admin;

    @ManyToOne
    private Role role;
}
