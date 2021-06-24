package com.wzp.nflj.security;

import com.wzp.nflj.model.BaseUser;
import com.wzp.nflj.model.Role;
import com.wzp.nflj.repository.AdminRoleRepository;
import com.wzp.nflj.service.BaseUserService;
import com.wzp.nflj.util.ObjUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 验证用户
 *
 * @Author: zp.wei
 * @DATE: 2020/8/31 14:51
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private BaseUserService baseUserServiceImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseUser realUser = baseUserServiceImpl.findByUsername(username);
        if (realUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return realUser;
    }
}
