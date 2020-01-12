package com.ledao.service;

import com.ledao.entity.Goods;

import java.util.List;

/**
 * 商品Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 12:07
 */
public interface GoodsService {

    /**
     * 查询某个类别下的所有商品
     *
     * @param typeId
     * @return
     */
    List<Goods> findByTypeId(Integer typeId);

    /**
     * 根据条件分页查询商品信息
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    List<Goods> list(Goods goods, Integer page, Integer rows);

    /**
     * 获取总记录数
     *
     * @param goods
     * @return
     */
    Long getCount(Goods goods);
}
