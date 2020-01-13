package com.ledao.repository;

import com.ledao.entity.PurchaseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 进货单Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:39
 */
public interface PurchaseListRepository extends JpaRepository<PurchaseList,Integer>, JpaSpecificationExecutor<PurchaseList> {
}
