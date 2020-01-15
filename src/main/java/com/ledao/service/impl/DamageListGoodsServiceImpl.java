package com.ledao.service.impl;

import com.ledao.entity.DamageListGoods;
import com.ledao.repository.DamageListGoodsRepository;
import com.ledao.service.DamageListGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品报损单商品Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:42
 */
@Service("damageListGoodsService")
public class DamageListGoodsServiceImpl implements DamageListGoodsService {

    @Resource
    private DamageListGoodsRepository damageListGoodsRepository;

    /**
     * 根据商品报损单id查询所有商品报损单商品
     *
     * @param damageListId
     * @return
     */
    @Override
    public List<DamageListGoods> listByDamageListId(Integer damageListId) {
        return damageListGoodsRepository.listByDamageListId(damageListId);
    }
}
