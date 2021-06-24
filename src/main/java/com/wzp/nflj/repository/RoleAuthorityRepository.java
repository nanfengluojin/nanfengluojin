package com.wzp.nflj.repository;

import com.wzp.nflj.model.Authority;
import com.wzp.nflj.model.Role;
import com.wzp.nflj.model.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author wzp
 * @Date 2021/4/20 11:34
 **/
@Repository
public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, Long>, QuerydslPredicateExecutor {

    @Query("select r.authority from RoleAuthority r where r.role=?1")
    List<Authority> findAuthorityByRole(Role role);

    @Query("select r.authority from RoleAuthority r where r.role in ?1")
    List<Authority> findAuthorityByRoleIn(List<Role> role);

    @Modifying
    void deleteAllByRole(Role role);

    @Modifying
    void deleteAllByAuthority(Authority authority);
}
