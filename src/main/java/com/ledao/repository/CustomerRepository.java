package com.ledao.repository;

import com.ledao.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 客户Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 22:47
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {

    /**
     * 根据名称模糊查询客户信息
     *
     * @param name
     * @return
     */
    @Query(value = "select * from t_customer where name like ?1",nativeQuery = true)
    List<Customer> findByName(String name);
}
