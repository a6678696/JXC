package com.ledao.service;

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
}
