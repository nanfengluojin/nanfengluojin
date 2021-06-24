package com.wzp.nflj.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色权限关系表
 */
@Entity
@Getter
@Setter
public class RoleAuthority implements Serializable {
    private static final long serialVersionUID = -6836333794014575714L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Authority authority;
}
