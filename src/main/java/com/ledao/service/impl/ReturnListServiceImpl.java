package com.ledao.service.impl;

import com.ledao.entity.Goods;
import com.ledao.entity.ReturnList;
import com.ledao.entity.ReturnListGoods;
import com.ledao.repository.GoodsRepository;
import com.ledao.repository.GoodsTypeRepository;
import com.ledao.repository.ReturnListGoodsRepository;
import com.ledao.repository.ReturnListRepository;
import com.ledao.service.ReturnListService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 退货单Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:44
 */
@Service("returnListService")
@Transactional(rollbackFor = Exception.class)
public class ReturnListServiceImpl implements ReturnListService {

    @Resource
    private ReturnListRepository returnListRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private ReturnListGoodsRepository returnListGoodsRepository;

    /**
     * 获取当天最大退货单号
     *
     * @return
     */
    @Override
    public String getTodayMaxReturnNumber() {
        return returnListRepository.getTodayMaxReturnNumber();
    }

    /**
     * 添加退货单 以及所有退货单商品 以及 修改商品成本价 库存数量 上次进价
     *
     * @param returnList
     * @param returnListGoodsList
     */
    @Override
    public void save(ReturnList returnList, List<ReturnListGoods> returnListGoodsList) {
        for (ReturnListGoods returnListGoods : returnListGoodsList) {
            //设置类别
            returnListGoods.setType(goodsTypeRepository.findById(returnListGoods.getTypeId()).get());
            //设置退货单
            returnListGoods.setReturnList(returnList);
            returnListGoodsRepository.save(returnListGoods);
            // 修改商品库存 成本均价 以及上次进价
            Goods goods = goodsRepository.findById(returnListGoods.getGoodsId()).get();
            goods.setInventoryQuantity(goods.getInventoryQuantity() - returnListGoods.getNum());
            goods.setState(2);
            goodsRepository.save(goods);
        }
        returnList.setReturnDate(new Date());
        returnListRepository.save(returnList);
    }
}
