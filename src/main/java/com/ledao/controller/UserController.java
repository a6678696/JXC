package com.ledao.controller;

import com.ledao.entity.Role;
import com.ledao.entity.User;
import com.ledao.service.RoleService;
import com.ledao.service.UserService;
import com.ledao.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
