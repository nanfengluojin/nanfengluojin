package com.wzp.nflj.repository;


import com.wzp.nflj.model.Admin;
import com.wzp.nflj.model.AdminRole;
import com.wzp.nflj.model.Role;
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
public interface AdminRoleRepository extends JpaRepository<AdminRole, Long>, QuerydslPredicateExecutor {

    @Query("select a.role from AdminRole a  where a.admin=?1")
    List<Role> findRoleByAdmin(Admin admin);

    @Query("select a.role from AdminRole a  where a.admin.id=?1")
    List<Role> findRoleByAdminId(Long id);

    @Modifying
    void deleteAllByRole(Role role);

    @Modifying
    void deleteAllByAdmin(Admin admin);
}
