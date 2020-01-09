package com.ledao.service;

/**
 * 用户角色关联service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 22:35
 */
public interface UserRoleService {

    /**
     * 根据用户id删除所有关联信息
     *
     * @param userId
     */
    void deleteByUserId(Integer userId);
}
