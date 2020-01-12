package com.ledao.repository;

import com.ledao.entity.GoodsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 商品类别Repository接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 10:31
 */
public interface GoodsTypeRepository extends JpaRepository<GoodsType, Integer>, JpaSpecificationExecutor<GoodsType> {

    /**
     * 根据父节点查找所有子节点
     *
     * @param id
     * @return
     */
    @Query(value = "select * from t_goodstype where p_id=?1",nativeQuery = true)
    List<GoodsType> findByParentId(Integer id);
}
