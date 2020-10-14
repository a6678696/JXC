package com.ledao.service;

import com.ledao.entity.User;

import java.util.List;

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

    /**
     * 根据条件分页查询用户信息
     *
     * @param user
     * @param page
     * @param pageSize
     * @return
     */
    List<User> list(User user, Integer page, Integer pageSize);

    /**
     * 获取总记录数
     *
     * @param user
     * @return
     */
    Long getCount(User user);

    /**
     * 添加或者修改用户信息
     *
     * @param user
     */
    void save(User user);

    /**
     * 根据id删除用户
     *
     * @param userId
     */
    void delete(Integer userId);

    /**
     * 根据id查询实体
     *
     * @param userId
     * @return
     */
    User findById(Integer userId);
}
