package com.wzp.nflj.repository;

import com.wzp.nflj.model.Admin;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @Author wzp
 * @Date 2021/4/20 11:34
 **/
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, QuerydslPredicateExecutor {

    Admin findByUsername(String username);

    @Override
    @Cacheable(value = "admin")
    Optional<Admin> findById(Long id);

    @Query("select count (a.id) from Admin a")
    long findCount();

    @Override
    Page<Admin> findAll(Pageable pageable);

}
