package com.ledao.service;

import com.ledao.entity.OverflowList;
import com.ledao.entity.OverflowListGoods;

import java.util.List;

/**
 * 商品报溢单Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:41
 */
public interface OverflowListService {

    /**
     * 获取当天最大商品报溢单号
     *
     * @return
     */
    String getTodayMaxOverflowNumber();

    /**
     * 添加商品报溢单 以及所有商品报溢单商品  以及 修改商品成本价 库存数量 上次进价
     *
     * @param overflowList
     * @param overflowListGoodsList
     */
    void save(OverflowList overflowList, List<OverflowListGoods> overflowListGoodsList);

    /**
     * 根据条件查询商品报溢单信息
     *
     * @param overflowList
     * @return
     */
    List<OverflowList> list(OverflowList overflowList);

    /**
     * 根据id查询实体
     *
     * @param overflowId
     * @return
     */
    OverflowList findById(Integer overflowId);

    /**
     * 根据id删除商品报溢单信息 包括商品报溢单里的所有商品
     *
     * @param id
     */
    void delete(Integer id);
}
