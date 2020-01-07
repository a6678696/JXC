package com.ledao.repository;

import com.ledao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-07 22:31
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 根据用户名查找用户实体
     *
     * @param userName
     * @return
     */
    @Query(nativeQuery = true,value = "select * from t_user where user_name=?1")
    User findByUserName(String userName);
}
