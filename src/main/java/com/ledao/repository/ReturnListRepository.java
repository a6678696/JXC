package com.ledao.repository;

import com.ledao.entity.ReturnList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 退货单Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:39
 */
public interface ReturnListRepository extends JpaRepository<ReturnList, Integer>, JpaSpecificationExecutor<ReturnList> {

    /**
     * 获取当天最大退货单号
     *
     * @return
     */
    @Query(value = "select MAX(return_number) from t_return_list where to_days(return_date)=to_days(now())",nativeQuery = true)
    String getTodayMaxReturnNumber();
}
