package com.wzp.nflj.service;


import com.wzp.nflj.model.BaseUser;

public interface BaseUserService {

    BaseUser findByUsername(String username);
}
