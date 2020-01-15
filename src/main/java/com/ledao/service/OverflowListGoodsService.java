package com.ledao.service;

import com.ledao.entity.OverflowListGoods;

import java.util.List;

/**
 * 商品报溢单商品Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:41
 */
public interface OverflowListGoodsService {

    /**
     * 根据商品报溢单id查询所有商品报溢单商品
     *
     * @param overflowListId
     * @return
     */
    List<OverflowListGoods> listByOverflowListId(Integer overflowListId);
}
