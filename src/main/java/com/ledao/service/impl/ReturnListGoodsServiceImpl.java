package com.ledao.service.impl;

import com.ledao.repository.ReturnListGoodsRepository;
import com.ledao.service.ReturnListGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 退货单商品Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:42
 */
@Service("returnListGoodsService")
public class ReturnListGoodsServiceImpl implements ReturnListGoodsService {

    @Resource
    private ReturnListGoodsRepository returnListGoodsRepository;
}
