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

    /**
     * 查询所有角色信息
     *
     * @return
     */
    List<Role> listAll();

    /**
     * 根据条件分页查询角色信息
     *
     * @param role
     * @param page
     * @param pageSize
     * @return
     */
    List<Role> list(Role role, Integer page, Integer pageSize);

    /**
     * 获取总记录数
     *
     * @param role
     * @return
     */
    Long getCount(Role role);

    /**
     * 添加或者修改角色信息
     *
     * @param role
     */
    void save(Role role);

    /**
     * 根据id删除角色
     *
     * @param roleId
     */
    void delete(Integer roleId);

    /**
     * 根据角色名查找角色实体
     *
     * @param roleName
     * @return
     */
    Role findByRoleName(String roleName);
}
