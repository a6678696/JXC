package com.ledao.service.impl;

import com.ledao.entity.OverflowList;
import com.ledao.entity.OverflowListGoods;
import com.ledao.entity.Goods;
import com.ledao.repository.OverflowListGoodsRepository;
import com.ledao.repository.OverflowListRepository;
import com.ledao.repository.GoodsRepository;
import com.ledao.repository.GoodsTypeRepository;
import com.ledao.service.OverflowListService;
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
 * 商品报溢单Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:44
 */
@Service("searchOverflowListService")
@Transactional(rollbackFor = Exception.class)
public class OverflowListServiceImpl implements OverflowListService {

    @Resource
    private OverflowListRepository overflowListRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private OverflowListGoodsRepository overflowListGoodsRepository;

    /**
     * 获取当天最大商品报溢单号
     *
     * @return
     */
    @Override
    public String getTodayMaxOverflowNumber() {
        return overflowListRepository.getTodayMaxOverflowNumber();
    }

    /**
     * 添加商品报溢单 以及所有商品报溢单商品 以及 修改商品成本价 库存数量 上次进价
     *
     * @param searchOverflowList
     * @param searchOverflowListGoodsList
     */
    @Override
    public void save(OverflowList searchOverflowList, List<OverflowListGoods> searchOverflowListGoodsList) {
        for (OverflowListGoods searchOverflowListGoods : searchOverflowListGoodsList) {
            //设置类别
            searchOverflowListGoods.setType(goodsTypeRepository.findById(searchOverflowListGoods.getTypeId()).get());
            //设置商品报溢单
            searchOverflowListGoods.setOverflowList(searchOverflowList);
            overflowListGoodsRepository.save(searchOverflowListGoods);
            // 修改商品库存
            Goods goods = goodsRepository.findById(searchOverflowListGoods.getGoodsId()).get();
            goods.setInventoryQuantity(goods.getInventoryQuantity() + searchOverflowListGoods.getNum());
            goods.setLastPurchasingPrice(searchOverflowListGoods.getPrice());
            goods.setState(2);
            goodsRepository.save(goods);
        }
        searchOverflowList.setOverflowDate(new Date());
        overflowListRepository.save(searchOverflowList);
    }

    @Override
    public List<OverflowList> list(OverflowList overflowList) {
        return overflowListRepository.findAll(new Specification<OverflowList>() {
            @Override
            public Predicate toPredicate(Root<OverflowList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (overflowList != null) {
                    if (StringUtil.isNotEmpty(overflowList.getOverflowNumber())) {
                        predicate.getExpressions().add(cb.like(root.get("overflowNumber"), "%" + overflowList.getOverflowNumber().trim() + "%"));
                    }
                    if (overflowList.getbOverflowDate() != null) {
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("overflowDate"), overflowList.getbOverflowDate()));
                    }
                    if (overflowList.geteOverflowDate() != null) {
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("overflowDate"), overflowList.geteOverflowDate()));
                    }
                }
                return predicate;
            }
        });
    }

    /**
     * 根据id查询实体
     *
     * @param overflowId
     * @return
     */
    @Override
    public OverflowList findById(Integer overflowId) {
        return overflowListRepository.findById(overflowId).get();
    }

    /**
     * 根据id删除商品报溢单信息 包括商品报溢单里的所有商品
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        overflowListGoodsRepository.deleteByOverflowListId(id);
        overflowListRepository.deleteById(id);
    }
}
