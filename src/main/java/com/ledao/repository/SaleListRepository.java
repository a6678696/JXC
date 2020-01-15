package com.ledao.repository;

import com.ledao.entity.SaleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 销售单Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:39
 */
public interface SaleListRepository extends JpaRepository<SaleList, Integer>, JpaSpecificationExecutor<SaleList> {

    /**
     * 获取当天最大销售单号
     *
     * @return
     */
    @Query(value = "select MAX(sale_number) from t_sale_list where to_days(sale_date)=to_days(now())", nativeQuery = true)
    String getTodayMaxSaleNumber();
}
