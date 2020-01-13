package com.ledao.service;

import com.ledao.entity.GoodsUnit;

import java.util.List;

/**
 * 商品单位Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 18:28
 */
public interface GoodsUnitService {

    /**
     * 查询所有商品单位信息
     *
     * @return
     */
    List<GoodsUnit> listAll();

    /**
     * 添加或者修改商品单位信息
     *
     * @param goodsUnit
     */
    void save(GoodsUnit goodsUnit);

    /**
     * 根据id删除商品单位
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    GoodsUnit findById(Integer id);
}
