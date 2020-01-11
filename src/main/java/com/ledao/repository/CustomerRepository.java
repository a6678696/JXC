package com.ledao.repository;

import com.ledao.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 客户Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 22:47
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {
}
