package com.ledao.repository;

import com.ledao.entity.PurchaseListGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 进货单商品Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:40
 */
public interface PurchaseListGoodsRepository extends JpaRepository<PurchaseListGoods, Integer>, JpaSpecificationExecutor<PurchaseListGoods> {

    /**
     * 根据进货单id查询所有进货单商品
     *
     * @param purchaseListId
     * @return
     */
    @Query(value = "select * from t_purchase_list_goods where purchase_list_id=?1", nativeQuery = true)
    List<PurchaseListGoods> listByPurchaseListId(Integer purchaseListId);

    /**
     * 根据进货单id删除所有进货单商品
     *
     * @param purchaseListId
     */
    @Query(value = "delete from t_purchase_list_goods where purchase_list_id=?1",nativeQuery = true)
    @Modifying
    void deleteByPurchaseListId(Integer purchaseListId);
}
