package com.ledao.service.impl;

import com.ledao.entity.Role;
import com.ledao.repository.RoleRepository;
import com.ledao.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * @param id
     * @return
     */
    @Override
    public List<Role> findByUserId(Integer id) {
        return roleRepository.findByUserId(id);
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
}
