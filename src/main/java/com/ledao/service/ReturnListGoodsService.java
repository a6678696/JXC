package com.ledao.service;

import com.ledao.entity.ReturnListGoods;

import java.util.List;

/**
 * 退货单商品Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:41
 */
public interface ReturnListGoodsService {

    /**
     * 根据退货单id查询所有退货单商品
     *
     * @param returnListId
     * @return
     */
    List<ReturnListGoods> listByReturnListId(Integer returnListId);
}
