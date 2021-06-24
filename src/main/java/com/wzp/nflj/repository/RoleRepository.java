package com.wzp.nflj.repository;


import com.wzp.nflj.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author wzp
 * @Date 2021/4/20 11:34
 **/
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, QuerydslPredicateExecutor {
}
