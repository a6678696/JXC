package com.ledao.service.impl;

import com.ledao.entity.Supplier;
import com.ledao.repository.SupplierRepository;
import com.ledao.service.SupplierService;
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
 * 供应商Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 21:14
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Resource
    private SupplierRepository supplierRepository;

    /**
     * 根据条件分页查询供应商信息
     *
     * @param supplier
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Supplier> list(Supplier supplier, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.Direction.ASC, "id");
        Page<Supplier> supplierPage = supplierRepository.findAll(new Specification<Supplier>() {
            @Override
            public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (supplier != null) {
                    if (StringUtil.isNotEmpty(supplier.getName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + supplier.getName() + "%"));
                    }
                }
                return predicate;
            }
        }, pageable);
        return supplierPage.getContent();
    }

    /**
     * 获取总记录数
     *
     * @param supplier
     * @return
     */
    @Override
    public Long getCount(Supplier supplier) {
        Long count = supplierRepository.count(new Specification<Supplier>() {
            @Override
            public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (supplier != null) {
                    if (StringUtil.isNotEmpty(supplier.getName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + supplier.getName() + "%"));
                    }
                }
                return predicate;
            }
        });
        return count;
    }

    /**
     * 添加或者修改供应商信息
     *
     * @param supplier
     */
    @Override
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    /**
     * 根据id删除供应商
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        supplierRepository.deleteById(id);
    }

    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    @Override
    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).get();
    }
}
