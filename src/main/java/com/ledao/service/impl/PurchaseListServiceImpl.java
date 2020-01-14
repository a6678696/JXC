package com.ledao.service.impl;

import com.ledao.entity.Goods;
import com.ledao.entity.PurchaseList;
import com.ledao.entity.PurchaseListGoods;
import com.ledao.repository.GoodsRepository;
import com.ledao.repository.GoodsTypeRepository;
import com.ledao.repository.PurchaseListGoodsRepository;
import com.ledao.repository.PurchaseListRepository;
import com.ledao.service.PurchaseListService;
import com.ledao.util.MathUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 进货单Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:44
 */
@Service("purchaseListService")
@Transactional(rollbackFor = Exception.class)
public class PurchaseListServiceImpl implements PurchaseListService {

    @Resource
    private PurchaseListRepository purchaseListRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private PurchaseListGoodsRepository purchaseListGoodsRepository;

    /**
     * 获取当天最大进货单号
     *
     * @return
     */
    @Override
    public String getTodayMaxPurchaseNumber() {
        return purchaseListRepository.getTodayMaxPurchaseNumber();
    }

    /**
     * 添加进货单 以及所有进货单商品 以及 修改商品成本价 库存数量 上次进价
     *
     * @param purchaseList
     * @param purchaseListGoodsList
     */
    @Override
    public void save(PurchaseList purchaseList, List<PurchaseListGoods> purchaseListGoodsList) {
        for (PurchaseListGoods purchaseListGoods : purchaseListGoodsList) {
            //设置类别
            purchaseListGoods.setType(goodsTypeRepository.findById(purchaseListGoods.getTypeId()).get());
            //设置进货单
            purchaseListGoods.setPurchaseList(purchaseList);
            purchaseListGoodsRepository.save(purchaseListGoods);
            // 修改商品库存 成本均价 以及上次进价
            Goods goods = goodsRepository.findById(purchaseListGoods.getGoodsId()).get();
            float svePurchasePrice = (goods.getPurchasingPrice() * goods.getInventoryQuantity() + purchaseListGoods.getPrice() * purchaseListGoods.getNum()) / (goods.getInventoryQuantity() + purchaseListGoods.getNum());
            goods.setPurchasingPrice(MathUtil.format2Bit(svePurchasePrice));
            goods.setInventoryQuantity(goods.getInventoryQuantity() + purchaseListGoods.getNum());
            goods.setLastPurchasingPrice(purchaseListGoods.getPrice());
            goods.setState(2);
            goodsRepository.save(goods);
        }
        purchaseList.setPurchaseDate(new Date());
        purchaseListRepository.save(purchaseList);
    }
}
