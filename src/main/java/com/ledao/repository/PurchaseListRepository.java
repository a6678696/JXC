package com.ledao.repository;

import com.ledao.entity.PurchaseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 进货单Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:39
 */
public interface PurchaseListRepository extends JpaRepository<PurchaseList, Integer>, JpaSpecificationExecutor<PurchaseList> {

    /**
     * 获取当天最大进货单号
     *
     * @return
     */
    @Query(value = "select MAX(purchase_number) from t_purchase_list where to_days(purchase_date)=to_days(now())", nativeQuery = true)
    String getTodayMaxPurchaseNumber();
}
