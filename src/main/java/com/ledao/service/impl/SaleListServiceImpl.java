package com.ledao.service.impl;

import com.ledao.entity.Goods;
import com.ledao.entity.SaleList;
import com.ledao.entity.SaleListGoods;
import com.ledao.repository.GoodsRepository;
import com.ledao.repository.GoodsTypeRepository;
import com.ledao.repository.SaleListGoodsRepository;
import com.ledao.repository.SaleListRepository;
import com.ledao.service.SaleListService;
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
 * 销售单Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:44
 */
@Service("searchSaleListService")
@Transactional(rollbackFor = Exception.class)
public class SaleListServiceImpl implements SaleListService {

    @Resource
    private SaleListRepository saleListRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private SaleListGoodsRepository saleListGoodsRepository;

    /**
     * 获取当天最大销售单号
     *
     * @return
     */
    @Override
    public String getTodayMaxSaleNumber() {
        return saleListRepository.getTodayMaxSaleNumber();
    }

    /**
     * 添加销售单 以及所有销售单商品 以及库存数量
     *
     * @param searchSaleList
     * @param searchSaleListGoodsList
     */
    @Override
    public void save(SaleList searchSaleList, List<SaleListGoods> searchSaleListGoodsList) {
        for (SaleListGoods searchSaleListGoods : searchSaleListGoodsList) {
            //设置类别
            searchSaleListGoods.setType(goodsTypeRepository.findById(searchSaleListGoods.getTypeId()).get());
            //设置销售单
            searchSaleListGoods.setSaleList(searchSaleList);
            saleListGoodsRepository.save(searchSaleListGoods);
            // 修改商品库存
            Goods goods = goodsRepository.findById(searchSaleListGoods.getGoodsId()).get();
            goods.setInventoryQuantity(goods.getInventoryQuantity() - searchSaleListGoods.getNum());
            goods.setLastPurchasingPrice(searchSaleListGoods.getPrice());
            goods.setState(2);
            goodsRepository.save(goods);
        }
        searchSaleList.setSaleDate(new Date());
        saleListRepository.save(searchSaleList);
    }

    @Override
    public List<SaleList> list(SaleList saleList) {
        return saleListRepository.findAll(new Specification<SaleList>() {
            @Override
            public Predicate toPredicate(Root<SaleList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (saleList != null) {
                    if (StringUtil.isNotEmpty(saleList.getSaleNumber())) {
                        predicate.getExpressions().add(cb.like(root.get("saleNumber"), "%" + saleList.getSaleNumber().trim() + "%"));
                    }
                    if (saleList.getCustomer() != null && saleList.getCustomer().getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("customer").get("id"), saleList.getCustomer().getId()));
                    }
                    if (saleList.getState() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("state"), saleList.getState()));
                    }
                    if (saleList.getBSaleDate() != null) {
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("saleDate"), saleList.getBSaleDate()));
                    }
                    if (saleList.getESaleDate() != null) {
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("saleDate"), saleList.getESaleDate()));
                    }
                }
                return predicate;
            }
        });
    }

    /**
     * 根据id查询实体
     *
     * @param saleId
     * @return
     */
    @Override
    public SaleList findById(Integer saleId) {
        return saleListRepository.findById(saleId).get();
    }

    /**
     * 根据id删除销售单信息 包括销售单里的所有商品
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        saleListGoodsRepository.deleteBySaleListId(id);
        saleListRepository.deleteById(id);
    }
}
