package com.ledao.repository;

import com.ledao.entity.ReturnListGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 退货单商品Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:40
 */
public interface ReturnListGoodsRepository extends JpaRepository<ReturnListGoods,Integer>, JpaSpecificationExecutor<ReturnListGoods> {
}
