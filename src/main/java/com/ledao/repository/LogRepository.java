package com.ledao.repository;

import com.ledao.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 系统日志Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 11:49
 */
public interface LogRepository extends JpaRepository<Log,Integer>, JpaSpecificationExecutor<Log> {
}
