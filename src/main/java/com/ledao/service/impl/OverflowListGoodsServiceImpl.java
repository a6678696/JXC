package com.ledao.service.impl;

import com.ledao.entity.OverflowListGoods;
import com.ledao.repository.OverflowListGoodsRepository;
import com.ledao.service.OverflowListGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品报溢单商品Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:42
 */
@Service("overflowListGoodsService")
public class OverflowListGoodsServiceImpl implements OverflowListGoodsService {

    @Resource
    private OverflowListGoodsRepository overflowListGoodsRepository;

    /**
     * 根据商品报溢单id查询所有商品报溢单商品
     *
     * @param overflowListId
     * @return
     */
    @Override
    public List<OverflowListGoods> listByOverflowListId(Integer overflowListId) {
        return overflowListGoodsRepository.listByOverflowListId(overflowListId);
    }
}
