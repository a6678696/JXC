package com.ledao.service;

import com.ledao.entity.User;
import org.springframework.data.domain.Sort;

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
}
