package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品单位实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 18:24
 */
@Data
@Entity
@Table(name = "t_goodsunit")
public class GoodsUnit {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品单位名称
     */
    @Column(length = 10)
    private String name;

    @Override
    public String toString() {
        return "--商品单位{" +
                "编号=" + id +
                ", 名称='" + name + '\'' +
                '}';
    }
}
