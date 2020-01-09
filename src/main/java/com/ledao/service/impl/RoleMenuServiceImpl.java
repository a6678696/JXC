package com.ledao.service.impl;

import com.ledao.repository.RoleMenuRepository;
import com.ledao.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 角色菜单关联Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-09 23:42
 */
@Service("roleMenuService")
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl implements RoleMenuService {

    @Resource
    private RoleMenuRepository roleMenuRepository;

    @Override
    public void deleteByRoleId(Integer roleId) {
        roleMenuRepository.deleteByRoleId(roleId);
    }
}
