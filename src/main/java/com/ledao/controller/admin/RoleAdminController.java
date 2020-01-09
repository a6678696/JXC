package com.ledao.controller.admin;

import com.ledao.entity.Role;
import com.ledao.repository.RoleRepository;
import com.ledao.service.RoleMenuService;
import com.ledao.service.RoleService;
import com.ledao.service.UserRoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理角色Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-09 15:16
 */
@RestController
@RequestMapping("/admin/role")
public class RoleAdminController {

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleMenuService roleMenuService;

    /**
     * 查询所有角色
     *
     * @return
     */
    @RequestMapping("/listAll")
    @RequiresPermissions(value = {"用户管理", "角色管理"}, logical = Logical.OR)
    public Map<String, Object> listAll() {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("rows", roleService.listAll());
        return resultMap;
    }

    /**
     * 分页查询角色信息
     *
     * @param searchRole
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = "角色管理")
    public Map<String, Object> list(Role searchRole, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<Role> roleList = roleService.list(searchRole, page, rows);
        Long total = roleService.getCount(searchRole);
        resultMap.put("rows", roleList);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 添加或者修改角色信息
     *
     * @param role
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "角色管理")
    public Map<String, Object> save(Role role) {
        Map<String, Object> resultMap = new HashMap<>(16);
        if (role.getId() == null) {
            //添加时判断用户名是否存在
            if (roleService.findByRoleName(role.getName()) != null) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "角色名已经存在!");
                return resultMap;
            }
        }
        roleService.save(role);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 删除角色信息
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = "角色管理")
    public Map<String, Object> delete(Integer roleId) {
        Map<String, Object> resultMap = new HashMap<>(16);
        roleMenuService.deleteByRoleId(roleId);
        userRoleService.deleteByRoleId(roleId);
        roleService.delete(roleId);
        resultMap.put("success", false);
        return resultMap;
    }
}
