package com.ledao.service.impl;

import com.ledao.entity.User;
import com.ledao.repository.UserRepository;
import com.ledao.service.UserService;
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
 * 用户Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 00:37
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 根据用户名查找用户实体
     *
     * @param userName
     * @return
     */
    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    /**
     * 根据条件分页查询用户信息
     *
     * @param user
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<User> list(User user, Integer page, Integer pageSize) {
        Pageable pageable=PageRequest.of(page-1, pageSize, Sort.Direction.ASC,"id");
        Page<User> userPage = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (user != null) {
                    if (StringUtil.isNotEmpty(user.getUserName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("userName"),"%" + user.getUserName() + "%"));
                    }
                    predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("id"), 1));
                }
                return predicate;
            }
        }, pageable);
        return userPage.getContent();
    }

    /**
     * 获取总记录数
     *
     * @param user
     * @return
     */
    @Override
    public Long getCount(User user) {
        Long count=userRepository.count(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (user != null) {
                    if (StringUtil.isNotEmpty(user.getUserName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("userName"),"%" + user.getUserName() + "%"));
                    }
                    predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("id"), 1));
                }
                return predicate;
            }
        });
        return count;
    }
}
