package com.ledao.service.impl;

import com.ledao.entity.SaleListGoods;
import com.ledao.repository.SaleListGoodsRepository;
import com.ledao.service.SaleListGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 销售单商品Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:42
 */
@Service("saleListGoodsService")
public class SaleListGoodsServiceImpl implements SaleListGoodsService {

    @Resource
    private SaleListGoodsRepository saleListGoodsRepository;

    /**
     * 根据销售单id查询所有销售单商品
     *
     * @param saleListId
     * @return
     */
    @Override
    public List<SaleListGoods> listBySaleListId(Integer saleListId) {
        return saleListGoodsRepository.listBySaleListId(saleListId);
    }

    /**
     * 统计某个商品的销售总数
     *
     * @param goodsId
     * @return
     */
    @Override
    public Integer getTotalByGoodsId(Integer goodsId) {
        return saleListGoodsRepository.getTotalByGoodsId(goodsId) == null ? 0 : saleListGoodsRepository.getTotalByGoodsId(goodsId);
    }
}
