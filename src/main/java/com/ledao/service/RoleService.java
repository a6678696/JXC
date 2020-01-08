package com.ledao.service;

import com.ledao.entity.Role;

import java.util.List;

/**
 * 角色Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 00:36
 */
public interface RoleService {

    /**
     * 根据用户id查角色集合
     *
     * @param userId
     * @return
     */
    List<Role> findByUserId(Integer userId);

    /**
     * 根据roleId查询实体
     *
     * @param roleId
     * @return
     */
    Role findById(Integer roleId);
}
