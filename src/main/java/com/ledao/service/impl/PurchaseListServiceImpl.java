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
import com.ledao.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * 进货单Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:44
 */
@Service("searchPurchaseListService")
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
     * @param searchPurchaseList
     * @param searchPurchaseListGoodsList
     */
    @Override
    public void save(PurchaseList searchPurchaseList, List<PurchaseListGoods> searchPurchaseListGoodsList) {
        for (PurchaseListGoods searchPurchaseListGoods : searchPurchaseListGoodsList) {
            //设置类别
            searchPurchaseListGoods.setType(goodsTypeRepository.findById(searchPurchaseListGoods.getTypeId()).get());
            //设置进货单
            searchPurchaseListGoods.setPurchaseList(searchPurchaseList);
            purchaseListGoodsRepository.save(searchPurchaseListGoods);
            // 修改商品库存 成本均价 以及上次进价
            Goods goods = goodsRepository.findById(searchPurchaseListGoods.getGoodsId()).get();
            float svePurchasePrice = (goods.getPurchasingPrice() * goods.getInventoryQuantity() + searchPurchaseListGoods.getPrice() * searchPurchaseListGoods.getNum()) / (goods.getInventoryQuantity() + searchPurchaseListGoods.getNum());
            goods.setPurchasingPrice(MathUtil.format2Bit(svePurchasePrice));
            goods.setInventoryQuantity(goods.getInventoryQuantity() + searchPurchaseListGoods.getNum());
            goods.setLastPurchasingPrice(searchPurchaseListGoods.getPrice());
            goods.setState(2);
            goodsRepository.save(goods);
        }
        searchPurchaseList.setPurchaseDate(new Date());
        purchaseListRepository.save(searchPurchaseList);
    }

    @Override
    public List<PurchaseList> list(PurchaseList purchaseList) {
        return purchaseListRepository.findAll(new Specification<PurchaseList>() {
            @Override
            public Predicate toPredicate(Root<PurchaseList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (purchaseList != null) {
                    if (StringUtil.isNotEmpty(purchaseList.getPurchaseNumber())) {
                        predicate.getExpressions().add(cb.like(root.get("purchaseNumber"), "%" + purchaseList.getPurchaseNumber().trim() + "%"));
                    }
                    if (purchaseList.getSupplier() != null && purchaseList.getSupplier().getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("supplier").get("id"), purchaseList.getSupplier().getId()));
                    }
                    if (purchaseList.getState() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("state"), purchaseList.getState()));
                    }
                    if (purchaseList.getBPurchaseDate() != null) {
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("purchaseDate"), purchaseList.getBPurchaseDate()));
                    }
                    if (purchaseList.getEPurchaseDate() != null) {
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("purchaseDate"), purchaseList.getEPurchaseDate()));
                    }
                }
                return predicate;
            }
        });
    }

    /**
     * 根据id查询实体
     *
     * @param purchaseId
     * @return
     */
    @Override
    public PurchaseList findById(Integer purchaseId) {
        return purchaseListRepository.findById(purchaseId).get();
    }

    /**
     * 根据id删除进货单信息 包括进货单里的所有商品
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        purchaseListGoodsRepository.deleteByPurchaseListId(id);
        purchaseListRepository.deleteById(id);
    }

    /**
     * 更新进货单
     *
     * @param purchaseList
     */
    @Override
    public void update(PurchaseList purchaseList) {
        purchaseListRepository.save(purchaseList);
    }
}
