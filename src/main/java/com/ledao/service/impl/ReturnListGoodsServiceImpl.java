package com.ledao.service.impl;

import com.ledao.entity.ReturnListGoods;
import com.ledao.repository.ReturnListGoodsRepository;
import com.ledao.service.ReturnListGoodsService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    @Override
    public List<ReturnListGoods> listByReturnListId(Integer returnListId) {
        return returnListGoodsRepository.listByReturnListId(returnListId);
    }

    @Override
    public List<ReturnListGoods> list(ReturnListGoods returnListGoods) {
        return returnListGoodsRepository.findAll(new Specification<ReturnListGoods>() {
            @Override
            public Predicate toPredicate(Root<ReturnListGoods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (returnListGoods != null) {
                    if (returnListGoods.getTypeId() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("type"), returnListGoods.getType()));
                    }
                    if (returnListGoods.getCodeOrName() != null) {
                        predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), "%" + returnListGoods.getCodeOrName() + "%"), criteriaBuilder.like(root.get("name"), "%" + returnListGoods.getCodeOrName() + "%")));
                    }
                }
                return predicate;
            }
        });
    }
}
