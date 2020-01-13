package com.ledao.service.impl;

import com.ledao.repository.PurchaseListRepository;
import com.ledao.service.PurchaseListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 进货单Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:44
 */
@Service("purchaseListService")
public class PurchaseListServiceImpl implements PurchaseListService {

    @Resource
    private PurchaseListRepository purchaseListRepository;
}
