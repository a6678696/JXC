package com.ledao.controller;

import com.ledao.entity.User;
import com.ledao.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-07 22:38
 */
@Controller
@RestController("/user")
public class UserController {

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
            map.put("success", true);
            return map;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("errorInfo", "用户名或密码错误!");
            return map;
        }
    }
}
