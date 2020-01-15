package com.ledao.repository;

import com.ledao.entity.DamageListGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 商品报损单商品Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:40
 */
public interface DamageListGoodsRepository extends JpaRepository<DamageListGoods, Integer>, JpaSpecificationExecutor<DamageListGoods> {

    /**
     * 根据商品报损单id查询所有商品报损单商品
     *
     * @param damageListId
     * @return
     */
    @Query(value = "select * from t_damage_list_goods where damage_list_id=?1", nativeQuery = true)
    List<DamageListGoods> listByDamageListId(Integer damageListId);

    /**
     * 根据商品报损单id删除所有商品报损单商品
     *
     * @param damageListId
     */
    @Query(value = "delete from t_damage_list_goods where damage_list_id=?1",nativeQuery = true)
    @Modifying
    void deleteByDamageListId(Integer damageListId);
}
