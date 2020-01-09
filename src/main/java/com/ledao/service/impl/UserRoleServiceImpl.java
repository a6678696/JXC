package com.ledao.service.impl;

import com.ledao.entity.UserRole;
import com.ledao.repository.UserRoleRepository;
import com.ledao.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户角色关联Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 22:35
 */
@Service("userRoleService")
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleRepository userRoleRepository;

    /**
     * 根据用户id删除所有关联信息
     *
     * @param userId
     */
    @Override
    public void deleteByUserId(Integer userId) {
        userRoleRepository.deleteByUserId(userId);
    }

    /**
     * 保存或修改用户角色关联
     *
     * @param userRole
     */
    @Override
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    /**
     * 根据角色id删除所有关联信息
     *
     * @param roleId
     */
    @Override
    public void deleteByRoleId(Integer roleId) {
        userRoleRepository.deleteByRoleId(roleId);
    }
}
