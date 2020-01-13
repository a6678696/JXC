package com.ledao.service.impl;

import com.ledao.repository.PurchaseListGoodsRepository;
import com.ledao.service.PurchaseListGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 进货单商品Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:42
 */
@Service("purchaseListGoodsService")
public class PurchaseListGoodsServiceImpl implements PurchaseListGoodsService {

    @Resource
    private PurchaseListGoodsRepository purchaseListGoodsRepository;
}
