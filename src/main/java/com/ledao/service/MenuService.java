package com.ledao.service;

import com.ledao.entity.Menu;

import java.util.List;

/**
 * 权限菜单Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 02:01
 */
public interface MenuService {

    /**
     * 根据父节点以及用户角色id查询子节点
     *
     * @param parentId
     * @param roleId
     * @return
     */
    List<Menu> findByParentIdAndRoleId(Integer parentId, Integer roleId);
}
