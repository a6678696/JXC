package com.ledao.repository;

import com.ledao.entity.OverflowListGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 商品报溢单商品Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:40
 */
public interface OverflowListGoodsRepository extends JpaRepository<OverflowListGoods, Integer>, JpaSpecificationExecutor<OverflowListGoods> {

    /**
     * 根据商品报溢单id查询所有商品报溢单商品
     *
     * @param overflowListId
     * @return
     */
    @Query(value = "select * from t_overflow_list_goods where overflow_list_id=?1", nativeQuery = true)
    List<OverflowListGoods> listByOverflowListId(Integer overflowListId);

    /**
     * 根据商品报溢单id删除所有商品报溢单商品
     *
     * @param overflowListId
     */
    @Query(value = "delete from t_overflow_list_goods where overflow_list_id=?1",nativeQuery = true)
    @Modifying
    void deleteByOverflowListId(Integer overflowListId);
}
