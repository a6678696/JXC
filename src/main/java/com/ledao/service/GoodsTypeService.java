package com.ledao.service;

import com.ledao.entity.GoodsType;

import java.util.List;

/**
 * 商品类别Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 10:34
 */
public interface GoodsTypeService {

    /**
     * 根据父节点查找所有子节点
     *
     * @param parentId
     * @return
     */
    List<GoodsType> findByParentId(Integer parentId);

    /**
     * 添加或者修改商品类别信息
     *
     * @param goodsType
     */
    void save(GoodsType goodsType);

    /**
     * 根据id删除商品类别
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
    GoodsType findById(Integer id);
}
