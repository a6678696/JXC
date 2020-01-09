package com.ledao.repository;

import com.ledao.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 用户角色关联Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 22:31
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>, JpaSpecificationExecutor<UserRole> {

    /**
     * 根据用户id删除所有关联信息
     *
     * @param userId
     */
    @Query(value="delete from t_user_role where user_id=?1",nativeQuery=true)
    @Modifying
    void deleteByUserId(Integer userId);
}
