package com.ledao.repository;

import com.ledao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 角色Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 00:28
 */
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    /**
     * 根据用户id查角色集合
     *
     * @param id
     * @return
     */
    @Query(nativeQuery = true, value = "select r.* from t_user u,t_role r,t_user_role ur where ur.`user_id`=u.`id` and ur.`role_id`=r.`id` and u.`id`=?1")
    List<Role> findByUserId(Integer id);

    /**
     * 根据角色名查找角色实体
     *
     * @param roleName
     * @return
     */
    @Query(nativeQuery = true,value = "select * from t_role where name=?1")
    Role findByRoleName(String roleName);
}
