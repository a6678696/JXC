package com.ledao.service.impl;

import com.ledao.entity.ReturnListGoods;
import com.ledao.repository.ReturnListGoodsRepository;
import com.ledao.service.ReturnListGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 根据退货单id查询所有退货单商品
     *
     * @param returnListId
     * @return
     */
    @Override
    public List<ReturnListGoods> listByReturnListId(Integer returnListId) {
        return returnListGoodsRepository.listByReturnListId(returnListId);
    }
}
