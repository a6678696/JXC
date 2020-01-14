package com.ledao.service;

import com.ledao.entity.ReturnList;
import com.ledao.entity.ReturnListGoods;

import java.util.List;

/**
 * 退货单Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:41
 */
public interface ReturnListService {

    /**
     * 获取当天最大退货单号
     *
     * @return
     */
    String getTodayMaxReturnNumber();

    /**
     * 添加退货单 以及所有退货单商品  以及 修改商品成本价 库存数量 上次进价
     *
     * @param returnList
     * @param returnListGoodsList
     */
    void save(ReturnList returnList, List<ReturnListGoods> returnListGoodsList);
}
