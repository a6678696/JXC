package com.ledao.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ledao.entity.Log;
import com.ledao.entity.Menu;
import com.ledao.entity.Role;
import com.ledao.entity.User;
import com.ledao.service.LogService;
import com.ledao.service.MenuService;
import com.ledao.service.RoleService;
import com.ledao.service.UserService;
import com.ledao.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-07 22:38
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @Resource
    private LogService logService;

    /**
     * 用户登录判断
     *
     * @param imageCode
     * @param user
     * @param bindingResult
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> login(String imageCode, @Valid User user, BindingResult bindingResult, HttpSession session) {
        Map<String, Object> map = new HashMap<>(16);
        //判断用户名和密码是否输入
        if (bindingResult.hasErrors()) {
            map.put("success", false);
            map.put("errorInfo", bindingResult.getFieldError().getDefaultMessage());
            return map;
        }
        if (StringUtil.isEmpty(imageCode)) {
            map.put("success", false);
            map.put("errorInfo", "请输入验证码!");
            return map;
        }
        if (!session.getAttribute("checkcode").equals(imageCode)) {
            map.put("success", false);
            map.put("errorInfo", "验证码输入错误!");
            return map;
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        try {
            subject.login(token);
            String userName = SecurityUtils.getSubject().getPrincipal().toString();
            User currentUser = userService.findByUserName(userName);
            session.setAttribute("currentUser", currentUser);
            List<Role> roleList = roleService.findByUserId(currentUser.getId());
            map.put("roleList", roleList);
            map.put("roleSize", roleList.size());
            map.put("success", true);
            logService.save(new Log(Log.LOGIN_ACTION, "用户登录"));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("errorInfo", "用户名或密码错误!");
            return map;
        }
    }

    /**
     * 保存角色信息
     *
     * @param roleId
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveRole")
    public Map<String, Object> saveRole(Integer roleId, HttpSession session) {
        Map<String, Object> map = new HashMap<>(16);
        Role currentRole = roleService.findById(roleId);
        session.setAttribute("currentRole", currentRole);
        map.put("success", true);
        return map;
    }

    /**
     * 加载当前用户信息
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/loadUserInfo")
    public String loadUserInfo(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Role currentRole = (Role) session.getAttribute("currentRole");
        return "欢迎您:" + currentUser.getTrueName() + "&nbsp;[&nbsp;" + currentRole.getName() + "&nbsp;]";
    }

    /**
     * 加载权限菜单
     *
     * @param session
     * @param parentId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/loadMenuInfo")
    public String loadMenuInfo(HttpSession session, Integer parentId) throws Exception {
        Role currentRole = (Role) session.getAttribute("currentRole");
        return getAllMenuByParentId(parentId, currentRole.getId()).toString();
    }

    /**
     * 获取所有菜单信息
     *
     * @param parentId
     * @param roleId
     * @return
     */
    public JsonArray getAllMenuByParentId(Integer parentId, Integer roleId) {
        JsonArray jsonArray = this.getMenuByParentId(parentId, roleId);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            if ("open".equals(jsonObject.get("state").getAsString())) {
                continue;
            } else {
                jsonObject.add("children", getAllMenuByParentId(jsonObject.get("id").getAsInt(), roleId));
            }
        }
        return jsonArray;
    }

    /**
     * 根据父节点和用户角色Id查询菜单
     *
     * @param parentId
     * @param roleId
     * @return
     */
    public JsonArray getMenuByParentId(Integer parentId, Integer roleId) {
        List<Menu> menuList = menuService.findByParentIdAndRoleId(parentId, roleId);
        JsonArray jsonArray = new JsonArray();
        for (Menu menu : menuList) {
            JsonObject jsonObject = new JsonObject();
            // 节点Id
            jsonObject.addProperty("id", menu.getId());
            // 节点名称
            jsonObject.addProperty("text", menu.getName());
            if (menu.getState() == 1) {
                // 根节点
                jsonObject.addProperty("state", "closed");
            } else {
                // 叶子节点
                jsonObject.addProperty("state", "open");
            }
            // 节点图标
            jsonObject.addProperty("iconCls", menu.getIcon());
            // 扩展属性
            JsonObject attributeObject = new JsonObject();
            // 菜单请求地址
            attributeObject.addProperty("url", menu.getUrl());
            jsonObject.add("attributes", attributeObject);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
