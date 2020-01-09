package com.ledao.service;

/**
 * 角色菜单关联service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-09 23:41
 */
public interface RoleMenuService {

    /**
     * 根据角色id删除所有关联信息
     *
     * @param roleId
     */
    void deleteByRoleId(Integer roleId);
}
