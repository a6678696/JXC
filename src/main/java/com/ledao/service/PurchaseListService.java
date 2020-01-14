package com.ledao.service;

import com.ledao.entity.PurchaseList;
import com.ledao.entity.PurchaseListGoods;

import java.util.List;

/**
 * 进货单Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:41
 */
public interface PurchaseListService {

    /**
     * 获取当天最大进货单号
     *
     * @return
     */
    String getTodayMaxPurchaseNumber();

    /**
     * 添加进货单 以及所有进货单商品  以及 修改商品成本价 库存数量 上次进价
     *
     * @param purchaseList
     * @param purchaseListGoodsList
     */
    void save(PurchaseList purchaseList, List<PurchaseListGoods> purchaseListGoodsList);
}
