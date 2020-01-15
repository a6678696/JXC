package com.ledao.service;

import com.ledao.entity.CustomerReturnListGoods;

import java.util.List;

/**
 * @author LeDao
 * @company
 * @create 2020-01-15 14:52
 */
public interface CustomerReturnListGoodsService {

    /**
     * 根据客户退货单id查询所有客户退货单商品
     *
     * @param customerReturnListId
     * @return
     */
    List<CustomerReturnListGoods> listByCustomerReturnListId(Integer customerReturnListId);

    /**
     * 统计某个商品的退货总数
     *
     * @param goodsId
     * @return
     */
    Integer getTotalByGoodsId(Integer goodsId);
}
