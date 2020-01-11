package com.ledao.controller.admin;

import com.ledao.entity.Log;
import com.ledao.entity.Role;
import com.ledao.entity.User;
import com.ledao.entity.UserRole;
import com.ledao.service.LogService;
import com.ledao.service.RoleService;
import com.ledao.service.UserRoleService;
import com.ledao.service.UserService;
import com.ledao.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private LogService logService;

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
        logService.save(new Log(Log.SEARCH_ACTION, "查询用户信息"));
        return resultMap;
    }

    /**
     * 添加或者修改用户信息
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Map<String, Object> save(User user) {
        Map<String, Object> resultMap = new HashMap<>(16);
        //添加用户时
        if (user.getId() == null) {
            if (userService.findByUserName(user.getUserName()) != null) {
                resultMap.put("success", false);
                resultMap.put("errorInfo", "您要注册的用户名已经存在!");
                return resultMap;
            }
        }
        if (user.getId() != null) {
            logService.save(new Log(Log.UPDATE_ACTION, "更新用户信息" + user));
        } else {
            logService.save(new Log(Log.ADD_ACTION, "添加用户信息" + user));
        }
        userService.save(user);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Map<String, Object> delete(Integer id) {
        logService.save(new Log(Log.DELETE_ACTION, "删除用户信息" + userService.findById(id)));
        Map<String, Object> resultMap = new HashMap<>(16);
        userRoleService.deleteByUserId(id);
        userService.delete(id);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 保存用户角色设置(先删除原来的再添加新的)
     *
     * @param roleIds
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveRoleSet")
    @RequiresPermissions(value = "用户管理")
    public Map<String, Object> saveRoleSet(String roleIds, Integer userId) {
        Map<String, Object> resultMap = new HashMap<>(16);
        userRoleService.deleteByUserId(userId);
        if (StringUtil.isNotEmpty(roleIds)) {
            String[] roleIdStr = roleIds.split(",");
            for (int i = 0; i < roleIdStr.length; i++) {
                UserRole userRole = new UserRole();
                userRole.setUser(userService.findById(userId));
                userRole.setRole(roleService.findById(Integer.parseInt(roleIdStr[i])));
                userRoleService.save(userRole);
            }
        }
        resultMap.put("success", true);
        logService.save(new Log(Log.UPDATE_ACTION, "保存用户角色设置"));
        return resultMap;
    }

    /**
     * 修改密码
     *
     * @param newPassword
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/modifyPassword")
    @RequiresPermissions(value = "修改密码")
    public Map<String, Object> modifyPassword(String newPassword, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>(16);
        User currentUser = (User) session.getAttribute("currentUser");
        User user = userService.findById(currentUser.getId());
        user.setPassword(newPassword);
        userService.save(user);
        resultMap.put("success", true);
        logService.save(new Log(Log.UPDATE_ACTION, "修改密码"));
        return resultMap;
    }

    /**
     * 安全退出
     *
     * @return
     */
    @GetMapping("/logout")
    @RequiresPermissions(value = "安全退出")
    public String logout(){
        logService.save(new Log(Log.LOGOUT_ACTION, "用户注销"));
        SecurityUtils.getSubject().logout();
        return "redirect:/login.html";
    }
}
