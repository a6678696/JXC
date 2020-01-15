package com.ledao.service.impl;

import com.ledao.entity.CustomerReturnListGoods;
import com.ledao.repository.CustomerReturnListGoodsRepository;
import com.ledao.service.CustomerReturnListGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户退货单商品Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-15 14:53
 */
@Service("customerReturnListGoodsService")
public class CustomerReturnListGoodsServiceImpl implements CustomerReturnListGoodsService {

    @Resource
    private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;

    @Override
    public List<CustomerReturnListGoods> listByCustomerReturnListId(Integer customerReturnListId) {
        return customerReturnListGoodsRepository.listByCustomerReturnListId(customerReturnListId);
    }
}
