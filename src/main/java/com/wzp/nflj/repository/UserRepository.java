package com.wzp.nflj.repository;


import com.wzp.nflj.model.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author wzp
 * @Date 2021/4/20 11:34
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor {

    User findByUsername(String username);


}
