package com.wzp.nflj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.List;
import java.util.Set;

/**
 * @Author wzp
 * @Date 2021/4/20 11:34
 **/
@ApiModel("用户基类model")
@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseUser extends Model implements UserDetails {

    private static final long serialVersionUID = 3567299296634319581L;

    @ApiModelProperty(value = "用户名")
    @Column(unique = true)
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;

    @Column(unique = true, nullable = false)
    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "登录激活")
    private Boolean enabled = true;

    @ApiModelProperty(value = "最后登录时间")
    private Long lastLoginTime;

    @ApiModelProperty(value = "最后登录IP")
    private String lastLoginIp;

    @ApiModelProperty(value = "注册IP")
    private String registerIp;

    @Transient
    private Set<GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public BaseUser() {
    }

    public BaseUser(String username, String password, Set<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Transient
    private List<Authority> authorityList;

    @Transient
    private List<Role> roleList;

    public BaseUser(Long id) {
        super(id);
    }
}
