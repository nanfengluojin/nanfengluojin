package com.wzp.nflj.config;

import com.wzp.nflj.model.Admin;
import com.wzp.nflj.model.User;
import com.wzp.nflj.repository.AdminRepository;
import com.wzp.nflj.repository.UserRepository;
import com.wzp.nflj.util.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zp.wei
 * @DATE: 2020/8/31 14:23
 */
@Slf4j
public class BaseConfig {

    @Resource
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices consumerTokenServices;
    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;
    @Resource
    private TokenEndpoint tokenEndpoint;
    @Resource
    public RestTemplate restTemplate;
    @Resource
    public RedisTemplate redisTemplate;
    @Resource
    private UserRepository userRepository;
    @Resource
    private AdminRepository adminRepository;


    /**
     * 获取token
     *
     * @param username
     * @param password
     * @return
     */
    public ResponseEntity<OAuth2AccessToken> getToken(String username, String password) {
        Map<String, String> parameters = new HashMap<>(3);
        parameters.put("grant_type", "password");
        parameters.put("username", username);
        parameters.put("password", password);
        ResponseEntity<OAuth2AccessToken> responseEntity = null;
        Authentication authentication = new UsernamePasswordAuthenticationToken(CustomConfig.withClient, CustomConfig.secret, null);
        try {
            responseEntity = tokenEndpoint.postAccessToken(authentication, parameters);
            log.info(String.valueOf(responseEntity.getBody()));
        } catch (Exception e) {
            log.error("授权失败");
            log.error(String.valueOf(e));
        }
        return responseEntity;
    }


    /**
     * 移除token
     *
     * @param username
     */
    public void removeToken(String username) {
        String key = "uname_to_access:" + CustomConfig.withClient + ":" + username;
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) redisTemplate.opsForList().index(key, 0);
        if (defaultOAuth2AccessToken != null && defaultOAuth2AccessToken.getValue() != null) {
            consumerTokenServices.revokeToken(defaultOAuth2AccessToken.getValue());
        }
    }


    /**
     * 从Security中获取用户信息
     *
     * @return
     */
    public UserDetails getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails;
        } else {
            System.err.println("获取用户信息失败");
            throw new AuthenticationServiceException("authentication not found");
        }
    }


    /**
     * 获取登录用户的用户名
     *
     * @return
     */
    protected String getUsername() {
        return getAuthentication().getUsername();
    }


    /**
     * 获取当前后台用户
     *
     * @return
     */
    protected Admin getAdmin() {
        String username = getUsername();
        if (username == null) {
            return null;
        }
        Admin admin = adminRepository.findByUsername(username);
        return admin;
    }


    /**
     * 获取当前登录用户
     *
     * @return
     */
    protected User getUser() {
        String username = getUsername();
        if (username == null) {
            return null;
        }
        User user = userRepository.findByUsername(username);
        return user;
    }


    /**
     * body参数分页器对象
     *
     * @param parameters
     * @return
     */
    protected PageRequest getPageRequest(Map<String, Object> parameters) {
        int size = 10;
        int page = 0;
        Sort.Direction direction = Sort.Direction.ASC;
        String properties = "id";
        Sort sort = null;
        if (!ObjUtil.isEmpty(size)) {
            size = Integer.valueOf(String.valueOf(parameters.get("size")));
        }
        if (!ObjUtil.isEmpty(page)) {
            page = Integer.valueOf(String.valueOf(parameters.get("page")));
        }
        if (!ObjUtil.isEmpty(sort)) {
            if (parameters.get("sort") instanceof List) {
                List<String> list = (List<String>) parameters.get("sort");
                if (list != null && !list.isEmpty()) {
                    List<Sort.Order> orders = new ArrayList<>();
                    for (String str : list) {
                        String[] strArray = str.split(",");
                        if (strArray.length >= 2) {
                            direction = Sort.Direction.valueOf((strArray[1]).toUpperCase());
                        }
                        Sort.Order order = new Sort.Order(direction, strArray[0]);
                        orders.add(order);
                    }
                    sort = Sort.by(orders);
                }
            } else {
                String str = String.valueOf(parameters.get("sort"));
                String[] strArray = str.split(",");
                if (strArray.length >= 2) {
                    direction = Sort.Direction.valueOf((strArray[1]).toUpperCase());
                }
                Sort.Order order = new Sort.Order(direction, strArray[0]);
                sort = Sort.by(order);
            }
        }
        if (sort == null) {
            sort = Sort.by(properties);
        }
        return PageRequest.of(page, size, sort);
    }


}
