package com.wzp.nflj.service.impl;


import com.wzp.nflj.model.BaseUser;
import com.wzp.nflj.repository.AdminRepository;
import com.wzp.nflj.repository.UserRepository;
import com.wzp.nflj.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseUserServiceImpl implements BaseUserService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public BaseUser findByUsername(String username) {
        BaseUser baseUser = adminRepository.findByUsername(username);
        if (baseUser == null) {
            baseUser = userRepository.findByUsername(username);
        }
        return baseUser;
    }
}
