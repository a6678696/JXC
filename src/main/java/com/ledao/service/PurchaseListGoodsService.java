package com.ledao.service;

import com.ledao.entity.PurchaseListGoods;

import java.util.List;

/**
 * 进货单商品Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:41
 */
public interface PurchaseListGoodsService {

    /**
     * 根据进货单id查询所有进货单商品
     *
     * @param purchaseListId
     * @return
     */
    List<PurchaseListGoods> listByPurchaseListId(Integer purchaseListId);

    /**
     * 根据条件查询进货单商品
     *
     * @param purchaseListGoods
     * @return
     */
    List<PurchaseListGoods> list(PurchaseListGoods purchaseListGoods);
}
