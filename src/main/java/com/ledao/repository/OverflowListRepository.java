package com.ledao.repository;

import com.ledao.entity.OverflowList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 商品报溢单Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:39
 */
public interface OverflowListRepository extends JpaRepository<OverflowList, Integer>, JpaSpecificationExecutor<OverflowList> {

    /**
     * 获取当天最大商品报溢单号
     *
     * @return
     */
    @Query(value = "select MAX(overflow_number) from t_overflow_list where to_days(overflow_date)=to_days(now())", nativeQuery = true)
    String getTodayMaxOverflowNumber();
}
