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

    @Override
    public List<Menu> findByParentIdAndRoleId(Integer parentId, Integer roleId) {
        return menuRepository.findByParentIdAndRoleId(parentId, roleId);
    }
}
