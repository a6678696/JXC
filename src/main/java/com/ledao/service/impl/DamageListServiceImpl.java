package com.ledao.service.impl;

import com.ledao.entity.DamageList;
import com.ledao.entity.DamageListGoods;
import com.ledao.entity.Goods;
import com.ledao.repository.DamageListGoodsRepository;
import com.ledao.repository.DamageListRepository;
import com.ledao.repository.GoodsRepository;
import com.ledao.repository.GoodsTypeRepository;
import com.ledao.service.DamageListService;
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
 * 商品报损单Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:44
 */
@Service("searchDamageListService")
@Transactional(rollbackFor = Exception.class)
public class DamageListServiceImpl implements DamageListService {

    @Resource
    private DamageListRepository damageListRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private DamageListGoodsRepository damageListGoodsRepository;

    /**
     * 获取当天最大商品报损单号
     *
     * @return
     */
    @Override
    public String getTodayMaxDamageNumber() {
        return damageListRepository.getTodayMaxDamageNumber();
    }

    /**
     * 添加商品报损单 以及所有商品报损单商品 以及 修改商品成本价 库存数量 上次进价
     *
     * @param searchDamageList
     * @param searchDamageListGoodsList
     */
    @Override
    public void save(DamageList searchDamageList, List<DamageListGoods> searchDamageListGoodsList) {
        for (DamageListGoods searchDamageListGoods : searchDamageListGoodsList) {
            //设置类别
            searchDamageListGoods.setType(goodsTypeRepository.findById(searchDamageListGoods.getTypeId()).get());
            //设置商品报损单
            searchDamageListGoods.setDamageList(searchDamageList);
            damageListGoodsRepository.save(searchDamageListGoods);
            // 修改商品库存
            Goods goods = goodsRepository.findById(searchDamageListGoods.getGoodsId()).get();
            goods.setInventoryQuantity(goods.getInventoryQuantity() - searchDamageListGoods.getNum());
            goods.setLastPurchasingPrice(searchDamageListGoods.getPrice());
            goods.setState(2);
            goodsRepository.save(goods);
        }
        searchDamageList.setDamageDate(new Date());
        damageListRepository.save(searchDamageList);
    }

    @Override
    public List<DamageList> list(DamageList damageList) {
        return damageListRepository.findAll(new Specification<DamageList>() {
            @Override
            public Predicate toPredicate(Root<DamageList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (damageList != null) {
                    if (StringUtil.isNotEmpty(damageList.getDamageNumber())) {
                        predicate.getExpressions().add(cb.like(root.get("damageNumber"), "%" + damageList.getDamageNumber().trim() + "%"));
                    }
                    if (damageList.getBDamageDate() != null) {
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("damageDate"), damageList.getBDamageDate()));
                    }
                    if (damageList.getEDamageDate() != null) {
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("damageDate"), damageList.getEDamageDate()));
                    }
                }
                return predicate;
            }
        });
    }

    /**
     * 根据id查询实体
     *
     * @param damageId
     * @return
     */
    @Override
    public DamageList findById(Integer damageId) {
        return damageListRepository.findById(damageId).get();
    }

    /**
     * 根据id删除商品报损单信息 包括商品报损单里的所有商品
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        damageListGoodsRepository.deleteByDamageListId(id);
        damageListRepository.deleteById(id);
    }
}
