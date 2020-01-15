package com.ledao.service.impl;

import com.ledao.entity.CustomerReturnList;
import com.ledao.entity.CustomerReturnListGoods;
import com.ledao.entity.Goods;
import com.ledao.repository.CustomerReturnListGoodsRepository;
import com.ledao.repository.CustomerReturnListRepository;
import com.ledao.repository.GoodsRepository;
import com.ledao.repository.GoodsTypeRepository;
import com.ledao.service.CustomerReturnListService;
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
 * 客户退货单Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-15 14:57
 */
@Service("customerReturnListService")
@Transactional(rollbackFor = Exception.class)
public class CustomerReturnListServiceImpl implements CustomerReturnListService {

    @Resource
    private CustomerReturnListRepository customerReturnListRepository;

    @Resource
    private GoodsTypeRepository goodsTypeRepository;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private CustomerReturnListGoodsRepository customerReturnListGoodsRepository;

    /**
     * 获取当天最大客户退货单号
     *
     * @return
     */
    @Override
    public String getTodayMaxCustomerReturnNumber() {
        return customerReturnListRepository.getTodayMaxCustomerReturnNumber();
    }

    /**
     * 添加客户退货单 以及所有客户退货单商品 以及 修改 库存数量
     *
     * @param customerReturnList
     * @param customerReturnListGoodsList
     */
    @Override
    public void save(CustomerReturnList customerReturnList, List<CustomerReturnListGoods> customerReturnListGoodsList) {
        for (CustomerReturnListGoods customerReturnListGoods : customerReturnListGoodsList) {
            // 设置类别
            customerReturnListGoods.setType(goodsTypeRepository.findById(customerReturnListGoods.getTypeId()).get());
            // 设置客户退货单
            customerReturnListGoods.setCustomerReturnList(customerReturnList);
            customerReturnListGoodsRepository.save(customerReturnListGoods);
            // 修改商品库存
            Goods goods = goodsRepository.findById(customerReturnListGoods.getGoodsId()).get();
            goods.setInventoryQuantity(goods.getInventoryQuantity() + customerReturnListGoods.getNum());
            goods.setState(2);
            goodsRepository.save(goods);
        }
        customerReturnList.setCustomerReturnDate(new Date());
        // 保存客户退货单
        customerReturnListRepository.save(customerReturnList);
    }

    /**
     * 根据条件查询客户退货单信息
     *
     * @param customerReturnList
     * @return
     */
    @Override
    public List<CustomerReturnList> list(CustomerReturnList customerReturnList) {
        return customerReturnListRepository.findAll(new Specification<CustomerReturnList>() {

            @Override
            public Predicate toPredicate(Root<CustomerReturnList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (customerReturnList != null) {
                    if (StringUtil.isNotEmpty(customerReturnList.getCustomerReturnNumber())) {
                        predicate.getExpressions().add(cb.like(root.get("customerReturnNumber"), "%" + customerReturnList.getCustomerReturnNumber().trim() + "%"));
                    }
                    if (customerReturnList.getCustomer() != null && customerReturnList.getCustomer().getId() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("customer").get("id"), customerReturnList.getCustomer().getId()));
                    }
                    if (customerReturnList.getState() != null) {
                        predicate.getExpressions().add(cb.equal(root.get("state"), customerReturnList.getState()));
                    }
                    if (customerReturnList.getBCustomerReturnDate() != null) {
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("customerReturnDate"), customerReturnList.getBCustomerReturnDate()));
                    }
                    if (customerReturnList.getECustomerReturnDate() != null) {
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("customerReturnDate"), customerReturnList.getECustomerReturnDate()));
                    }
                }
                return predicate;
            }
        });
    }

    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    @Override
    public CustomerReturnList findById(Integer id) {
        return customerReturnListRepository.findById(id).get();
    }

    /**
     * 根据id删除客户退货单信息 包括客户退货单里的所有商品
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        customerReturnListGoodsRepository.deleteByCustomerReturnListId(id);
        customerReturnListRepository.deleteById(id);
    }

    /**
     * 更新客户退货单
     *
     * @param customerReturnList
     */
    @Override
    public void update(CustomerReturnList customerReturnList) {
        customerReturnListRepository.save(customerReturnList);
    }
}
