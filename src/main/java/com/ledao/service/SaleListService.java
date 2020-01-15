package com.ledao.service;

import com.ledao.entity.SaleList;
import com.ledao.entity.SaleListGoods;

import java.util.List;

/**
 * 销售单Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:41
 */
public interface SaleListService {

    /**
     * 获取当天最大销售单号
     *
     * @return
     */
    String getTodayMaxSaleNumber();

    /**
     * 添加销售单 以及所有销售单商品  以及 修改商品成本价 库存数量 上次进价
     *
     * @param saleList
     * @param saleListGoodsList
     */
    void save(SaleList saleList, List<SaleListGoods> saleListGoodsList);

    /**
     * 根据条件查询销售单信息
     *
     * @param saleList
     * @return
     */
    List<SaleList> list(SaleList saleList);

    /**
     * 根据id查询实体
     *
     * @param saleId
     * @return
     */
    SaleList findById(Integer saleId);

    /**
     * 根据id删除销售单信息 包括销售单里的所有商品
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 更新销售单
     *
     * @param saleList
     */
    void update(SaleList saleList);
}
