package com.ledao.service;

import com.ledao.entity.User;

/**
 * 用户Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 00:34
 */
public interface UserService {

    /**
     * 根据用户名查找用户实体
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);
}
