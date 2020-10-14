package com.ledao.service.impl;

import com.ledao.entity.CustomerReturnListGoods;
import com.ledao.repository.CustomerReturnListGoodsRepository;
import com.ledao.service.CustomerReturnListGoodsService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    /**
     * 统计某个商品的退货总数
     *
     * @param goodsId
     * @return
     */
    @Override
    public Integer getTotalByGoodsId(Integer goodsId) {
        return customerReturnListGoodsRepository.getTotalByGoodsId(goodsId) == null ? 0 : customerReturnListGoodsRepository.getTotalByGoodsId(goodsId);
    }

    @Override
    public List<CustomerReturnListGoods> list(CustomerReturnListGoods customerReturnListGoods) {
        return customerReturnListGoodsRepository.findAll(new Specification<CustomerReturnListGoods>() {
            @Override
            public Predicate toPredicate(Root<CustomerReturnListGoods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (customerReturnListGoods != null) {
                    if (customerReturnListGoods.getTypeId() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("type"), customerReturnListGoods.getType()));
                    }
                    if (customerReturnListGoods.getCodeOrName() != null) {
                        predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), "%" + customerReturnListGoods.getCodeOrName() + "%"), criteriaBuilder.like(root.get("name"), "%" + customerReturnListGoods.getCodeOrName() + "%")));
                    }
                }
                return predicate;
            }
        });
    }
}
