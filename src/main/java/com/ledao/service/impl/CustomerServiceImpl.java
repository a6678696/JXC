package com.ledao.service.impl;

import com.ledao.entity.Customer;
import com.ledao.repository.CustomerRepository;
import com.ledao.service.CustomerService;
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
 * 客户Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 22:51
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerRepository customerRepository;

    /**
     * 根据条件分页查询客户信息
     *
     * @param customer
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Customer> list(Customer customer, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.ASC, "id");
        Page<Customer> customerPage = customerRepository.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (customer != null) {
                    if (StringUtil.isNotEmpty(customer.getName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + customer.getName() + "%"));
                    }
                }
                return predicate;
            }
        }, pageable);
        return customerPage.getContent();
    }

    /**
     * 获取总记录数
     *
     * @param customer
     * @return
     */
    @Override
    public Long getCount(Customer customer) {
        Long count = customerRepository.count(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (customer != null) {
                    if (StringUtil.isNotEmpty(customer.getName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + customer.getName() + "%"));
                    }
                }
                return predicate;
            }
        });
        return count;
    }

    /**
     * 添加或者修改客户信息
     *
     * @param customer
     */
    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    /**
     * 根据id删除客户
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }

    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    @Override
    public Customer findById(Integer id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }
}
