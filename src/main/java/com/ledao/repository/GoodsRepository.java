package com.ledao.repository;

import com.ledao.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 商品Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 12:05
 */
public interface GoodsRepository extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods> {
    /**
     * 查询某个类别下的所有商品
     *
     * @param typeId
     * @return
     */
    @Query(value = "select * from t_goods where type_id=?1",nativeQuery = true)
    List<Goods> findByTypeId(Integer typeId);
}
