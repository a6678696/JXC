package com.ledao.repository;

import com.ledao.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 菜单Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-08 01:56
 */
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    /**
     * 根据父节点以及用户角色id查询子节点
     *
     * @param parentId
     * @param roleId
     * @return
     */
    @Query(nativeQuery = true, value = "select * from t_menu where p_id=?1 and id in (select menu_id from t_role_menu where role_id=?2)")
    List<Menu> findByParentIdAndRoleId(Integer parentId, Integer roleId);
}
