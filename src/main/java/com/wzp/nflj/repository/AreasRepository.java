package com.wzp.nflj.repository;


import com.wzp.nflj.model.Areas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zp.wei
 * @DATE: 2021/3/31 10:54
 */
@Repository
public interface AreasRepository extends JpaRepository<Areas, Long>, QuerydslPredicateExecutor {

    List<Areas> findByParentId(Integer parentId);


}
