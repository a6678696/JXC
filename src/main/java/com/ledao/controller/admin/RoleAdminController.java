package com.ledao.controller.admin;

import com.ledao.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
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

    /**
     * 查询所有角色
     *
     * @return
     */
    @RequestMapping("/listAll")
    public Map<String, Object> listAll() {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("rows", roleService.listAll());
        return resultMap;
    }
}
