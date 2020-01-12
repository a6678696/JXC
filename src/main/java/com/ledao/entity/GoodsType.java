package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品类别实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 10:07
 */
@Data
@Entity
@Table(name = "t_goodstype")
public class GoodsType {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 商品列表名称
     */
    @Column(length = 50)
    private String name;
    /**
     * 类别节点类型 1 根节点 0 叶子节点
     */
    private Integer state;
    /**
     * 图标
     */
    @Column(length = 100)
    private String icon;
    /**
     * 父菜单Id
     */
    private Integer pId;

    @Override
    public String toString() {
        return "--{" +
                "编号=" + id +
                ", 商品列表名称='" + name + '\'' +
                ", 类别节点类型=" + state +
                ", 图标='" + icon + '\'' +
                ", 父菜单Id=" + pId +
                '}';
    }
}
