package com.ledao.controller.admin;

import com.ledao.entity.Role;
import com.ledao.entity.User;
import com.ledao.service.RoleService;
import com.ledao.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LeDao
 * @company
 * @create 2020-01-08 21:17
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    /**
     * 分页查询用户信息
     *
     * @param searchUser
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> list(User searchUser, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<User> userList = userService.list(searchUser, page, rows);
        //查询每个用户拥有的角色,并设置显示
        for (User user : userList) {
            List<Role> roleList = roleService.findByUserId(user.getId());
            StringBuffer sb = new StringBuffer();
            for (Role role : roleList) {
                sb.append("," + role.getName());
            }
            user.setRoles(sb.toString().replaceFirst(",", ""));
        }
        Long total = userService.getCount(searchUser);
        resultMap.put("rows", userList);
        resultMap.put("total", total);
        return resultMap;
    }
}
