package com.ledao.repository;

import com.ledao.entity.PurchaseListGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 进货单商品Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:40
 */
public interface PurchaseListGoodsRepository extends JpaRepository<PurchaseListGoods,Integer>, JpaSpecificationExecutor<PurchaseListGoods> {
}
