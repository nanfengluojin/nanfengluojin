package com.wzp.nflj.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/28 16:57
 */
@Configuration
public class TokenStoreConfig {

    @Resource
    RedisConnectionFactory redisConnectionFactory;

    /**
     * 将token存在redis里
     */
    @Bean
    TokenStore tokenStore() {
        return new RedisTokenStoreConfig(redisConnectionFactory);
    }


}
