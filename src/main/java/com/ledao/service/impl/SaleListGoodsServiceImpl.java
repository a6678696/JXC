package com.ledao.service.impl;

import com.ledao.entity.SaleListGoods;
import com.ledao.repository.SaleListGoodsRepository;
import com.ledao.service.SaleListGoodsService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 销售单商品Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:42
 */
@Service("saleListGoodsService")
public class SaleListGoodsServiceImpl implements SaleListGoodsService {

    @Resource
    private SaleListGoodsRepository saleListGoodsRepository;

    /**
     * 根据销售单id查询所有销售单商品
     *
     * @param saleListId
     * @return
     */
    @Override
    public List<SaleListGoods> listBySaleListId(Integer saleListId) {
        return saleListGoodsRepository.listBySaleListId(saleListId);
    }

    /**
     * 统计某个商品的销售总数
     *
     * @param goodsId
     * @return
     */
    @Override
    public Integer getTotalByGoodsId(Integer goodsId) {
        return saleListGoodsRepository.getTotalByGoodsId(goodsId) == null ? 0 : saleListGoodsRepository.getTotalByGoodsId(goodsId);
    }

    @Override
    public List<SaleListGoods> list(SaleListGoods saleListGoods) {
        return saleListGoodsRepository.findAll(new Specification<SaleListGoods>() {
            @Override
            public Predicate toPredicate(Root<SaleListGoods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (saleListGoods != null) {
                    if (saleListGoods.getTypeId() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("type"), saleListGoods.getType()));
                    }
                    if (saleListGoods.getCodeOrName() != null) {
                        predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), "%" + saleListGoods.getCodeOrName() + "%"), criteriaBuilder.like(root.get("name"), "%" + saleListGoods.getCodeOrName() + "%")));
                    }
                }
                return predicate;
            }
        });
    }
}
