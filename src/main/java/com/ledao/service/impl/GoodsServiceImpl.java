package com.ledao.service.impl;

import com.ledao.entity.Goods;
import com.ledao.repository.GoodsRepository;
import com.ledao.service.GoodsService;
import com.ledao.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 商品Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 12:08
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsRepository goodsRepository;

    /**
     * 查询某个类别下的所有商品
     *
     * @param typeId
     * @return
     */
    @Override
    public List<Goods> findByTypeId(Integer typeId) {
        return goodsRepository.findByTypeId(typeId);
    }

    /**
     * 根据条件分页查询商品信息
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @Override
    public List<Goods> list(Goods goods, Integer page, Integer rows) {
        Pageable pageable = PageRequest.of(page - 1, rows, Sort.Direction.ASC, "id");
        Page<Goods> goodsPage = goodsRepository.findAll(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (goods != null) {
                    if (StringUtil.isNotEmpty(goods.getName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + goods.getName() + "%"));
                    }
                    if (goods.getType() != null && goods.getType().getId() != null && goods.getType().getId() != 1) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("type").get("id"), goods.getType().getId()));
                    }
                }
                return predicate;
            }
        }, pageable);
        return goodsPage.getContent();
    }

    /**
     * 获取总记录数
     *
     * @param goods
     * @return
     */
    @Override
    public Long getCount(Goods goods) {
        Long count = goodsRepository.count(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (goods != null) {
                    if (StringUtil.isNotEmpty(goods.getName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + goods.getName() + "%"));
                    }
                    if (goods.getType() != null && goods.getType().getId() != null && goods.getType().getId() != 1) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("type").get("id"), goods.getType().getId()));
                    }
                }
                return predicate;
            }
        });
        return count;
    }

    /**
     * 根据商品编码或商品名称条件分页查询没有库存的商品信息
     *
     * @param codeOrName
     * @param page
     * @param rows
     * @return
     */
    @Override
    public List<Goods> listNoInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer rows) {
        Pageable pageable = PageRequest.of(page - 1, rows, Sort.Direction.ASC, "id");
        Page<Goods> goodsPage=goodsRepository.findAll(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (StringUtil.isNotEmpty(codeOrName)) {
                    predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), "%" + codeOrName + "%"), criteriaBuilder.like(root.get("name"), "%" + codeOrName + "%")));
                }
                //库存是0的
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("inventoryQuantity"), 0));
                return predicate;
            }
        },pageable);
        return goodsPage.getContent();
    }

    /**
     * 根据商品编码或商品名称条件分页查询没有库存的商品信息的总记录数
     *
     * @param codeOrName
     * @return
     */
    @Override
    public Long getCountNoInventoryQuantityByCodeOrName(String codeOrName) {
        Long count=goodsRepository.count(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (StringUtil.isNotEmpty(codeOrName)) {
                    predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), "%" + codeOrName + "%"), criteriaBuilder.like(root.get("name"), "%" + codeOrName + "%")));
                }
                //库存是0的
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("inventoryQuantity"), 0));
                return predicate;
            }
        });
        return count;
    }

    /**
     * 根据商品编码或商品名称条件分页查询有库存的商品信息
     *
     * @param codeOrName
     * @param page
     * @param rows
     * @return
     */
    @Override
    public List<Goods> listHasInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer rows) {
        Pageable pageable = PageRequest.of(page - 1, rows, Sort.Direction.ASC, "id");
        Page<Goods> goodsPage=goodsRepository.findAll(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (StringUtil.isNotEmpty(codeOrName)) {
                    predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), "%" + codeOrName + "%"), criteriaBuilder.like(root.get("name"), "%" + codeOrName + "%")));
                }
                //库存大于0的
                predicate.getExpressions().add(criteriaBuilder.greaterThan(root.get("inventoryQuantity"), 0));
                return predicate;
            }
        },pageable);
        return goodsPage.getContent();
    }

    /**
     * 根据商品编码或商品名称条件分页查询有库存的商品信息的总记录数
     *
     * @param codeOrName
     * @return
     */
    @Override
    public Long getCountHasInventoryQuantityByCodeOrName(String codeOrName) {
        Long count=goodsRepository.count(new Specification<Goods>() {
            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (StringUtil.isNotEmpty(codeOrName)) {
                    predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.like(root.get("code"), "%" + codeOrName + "%"), criteriaBuilder.like(root.get("name"), "%" + codeOrName + "%")));
                }
                //库存大于0的
                predicate.getExpressions().add(criteriaBuilder.greaterThan(root.get("inventoryQuantity"), 0));
                return predicate;
            }
        });
        return count;
    }

    /**
     * 获取最大的商品编码
     *
     * @return
     */
    @Override
    public String getMaxGoodsCode() {
        return goodsRepository.getMaxGoodsCode();
    }

    /**
     * 添加或者修改商品信息
     *
     * @param goods
     */
    @Override
    public void save(Goods goods) {
        goodsRepository.save(goods);
    }

    /**
     * 根据id删除商品
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        goodsRepository.deleteById(id);
    }

    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    @Override
    public Goods findById(Integer id) {
        return goodsRepository.findById(id).get();
    }
}
