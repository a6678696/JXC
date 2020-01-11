package com.ledao.repository;

import com.ledao.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 供应商Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 21:07
 */
public interface SupplierRepository extends JpaRepository<Supplier,Integer>, JpaSpecificationExecutor<Supplier> {
}
