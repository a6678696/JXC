package com.ledao.service;

import com.ledao.entity.DamageListGoods;

import java.util.List;

/**
 * 商品报损单商品Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:41
 */
public interface DamageListGoodsService {

    /**
     * 根据商品报损单id查询所有商品报损单商品
     *
     * @param damageListId
     * @return
     */
    List<DamageListGoods> listByDamageListId(Integer damageListId);
}
