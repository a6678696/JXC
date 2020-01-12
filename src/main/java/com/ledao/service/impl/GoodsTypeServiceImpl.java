package com.ledao.service.impl;

import com.ledao.entity.GoodsType;
import com.ledao.repository.GoodsTypeRepository;
import com.ledao.service.GoodsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品类别service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 10:36
 */
@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService {

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    /**
     * 根据父节点查找所有子节点
     *
     * @param parentId
     * @return
     */
    @Override
    public List<GoodsType> findByParentId(Integer parentId) {
        return goodsTypeRepository.findByParentId(parentId);
    }

    /**
     * 添加或者修改商品类别信息
     *
     * @param goodsType
     */
    @Override
    public void save(GoodsType goodsType) {
        goodsTypeRepository.save(goodsType);
    }

    /**
     * 根据id删除商品类别
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        goodsTypeRepository.deleteById(id);
    }

    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    @Override
    public GoodsType findById(Integer id) {
        return goodsTypeRepository.findById(id).get();
    }
}
