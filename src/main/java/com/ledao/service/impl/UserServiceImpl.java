package com.ledao.service.impl;

import com.ledao.entity.User;
import com.ledao.repository.UserRepository;
import com.ledao.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户Service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 00:37
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 根据用户名查找用户实体
     *
     * @param userName
     * @return
     */
    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
