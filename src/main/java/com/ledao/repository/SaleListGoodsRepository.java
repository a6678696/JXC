package com.ledao.repository;

import com.ledao.entity.SaleListGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 销售单商品Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:40
 */
public interface SaleListGoodsRepository extends JpaRepository<SaleListGoods, Integer>, JpaSpecificationExecutor<SaleListGoods> {

    /**
     * 根据销售单id查询所有销售单商品
     *
     * @param saleListId
     * @return
     */
    @Query(value = "select * from t_sale_list_goods where sale_list_id=?1", nativeQuery = true)
    List<SaleListGoods> listBySaleListId(Integer saleListId);

    /**
     * 根据销售单id删除所有销售单商品
     *
     * @param saleListId
     */
    @Query(value = "delete from t_sale_list_goods where sale_list_id=?1", nativeQuery = true)
    @Modifying
    void deleteBySaleListId(Integer saleListId);

    /**
     * 统计某个商品的销售总数
     *
     * @param goodsId
     * @return
     */
    @Query(value = "select sum(num) as total from t_sale_list_goods where goods_id=?1",nativeQuery = true)
    Integer getTotalByGoodsId(Integer goodsId);
}
