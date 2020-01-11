package com.ledao.service.impl;

import com.ledao.entity.Menu;
import com.ledao.repository.MenuRepository;
import com.ledao.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限菜单Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 02:02
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuRepository menuRepository;

    /**
     * 根据父节点以及用户角色id查询子节点
     *
     * @param parentId
     * @param roleId
     * @return
     */
    @Override
    public List<Menu> findByParentIdAndRoleId(Integer parentId, Integer roleId) {
        return menuRepository.findByParentIdAndRoleId(parentId, roleId);
    }

    /**
     * 根据父节点查找所有子节点
     *
     * @param parentId
     * @return
     */
    @Override
    public List<Menu> findByParentId(int parentId) {
        return menuRepository.findByParentId(parentId);
    }

    /**
     * 根据角色id获取菜单集合
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Menu> findByRoleId(Integer roleId) {
        return menuRepository.findByRoleId(roleId);
    }

    /**
     * 根据id获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Menu findById(Integer id) {
        return menuRepository.findById(id).get();
    }
}
