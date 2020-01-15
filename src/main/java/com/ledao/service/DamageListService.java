package com.ledao.service;

import com.ledao.entity.DamageList;
import com.ledao.entity.DamageListGoods;

import java.util.List;

/**
 * 商品报损单Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:41
 */
public interface DamageListService {

    /**
     * 获取当天最大商品报损单号
     *
     * @return
     */
    String getTodayMaxDamageNumber();

    /**
     * 添加商品报损单 以及所有商品报损单商品  以及 修改商品成本价 库存数量 上次进价
     *
     * @param damageList
     * @param damageListGoodsList
     */
    void save(DamageList damageList, List<DamageListGoods> damageListGoodsList);

    /**
     * 根据条件查询商品报损单信息
     *
     * @param damageList
     * @return
     */
    List<DamageList> list(DamageList damageList);

    /**
     * 根据id查询实体
     *
     * @param damageId
     * @return
     */
    DamageList findById(Integer damageId);

    /**
     * 根据id删除商品报损单信息 包括商品报损单里的所有商品
     *
     * @param id
     */
    void delete(Integer id);
}
