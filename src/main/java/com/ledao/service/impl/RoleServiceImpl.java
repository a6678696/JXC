package com.ledao.service.impl;

import com.ledao.entity.Role;
import com.ledao.repository.RoleRepository;
import com.ledao.service.RoleService;
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
 * 角色Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 00:39
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    /**
     * 根据用户id查角色集合
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> findByUserId(Integer userId) {
        return roleRepository.findByUserId(userId);
    }

    /**
     * 根据id查询实体
     *
     * @param roleId
     * @return
     */
    @Override
    public Role findById(Integer roleId) {
        return roleRepository.findById(roleId).get();
    }

    /**
     * 查询所有角色信息
     *
     * @return
     */
    @Override
    public List<Role> listAll() {
        return roleRepository.findAll();
    }

    /**
     * 根据条件分页查询角色信息
     *
     * @param role
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Role> list(Role role, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.Direction.ASC, "id");
        Page<Role> rolePage = roleRepository.findAll(new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (role != null) {
                    if (StringUtil.isNotEmpty(role.getName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + role.getName() + "%"));
                    }
                    predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("id"), 1));
                }
                return predicate;
            }
        }, pageable);
        return rolePage.getContent();
    }

    /**
     * 获取总记录数
     *
     * @param role
     * @return
     */
    @Override
    public Long getCount(Role role) {
        Long count = roleRepository.count(new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (role != null) {
                    if (StringUtil.isNotEmpty(role.getName())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%" + role.getName() + "%"));
                    }
                    predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("id"), 1));
                }
                return predicate;
            }
        });
        return count;
    }

    /**
     * 添加或者修改角色信息
     *
     * @param role
     */
    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    /**
     * 根据id删除角色
     *
     * @param roleId
     */
    @Override
    public void delete(Integer roleId) {
        roleRepository.deleteById(roleId);
    }

    /**
     * 根据角色名查找角色实体
     *
     * @param roleName
     * @return
     */
    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
