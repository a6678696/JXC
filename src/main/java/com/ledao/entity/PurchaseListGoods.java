package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 进货单商品实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:35
 */
@Data
@Entity
@Table(name = "t_purchaseListGoods")
public class PurchaseListGoods {

    /**
     * 编号
     */
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 进货单
     */
    @ManyToOne
    @JoinColumn(name = "purchaseListId")
    private PurchaseList purchaseList;
    /**
     * 商品编码
     */
    @Column(length = 50)
    private String code;
    /**
     * 商品名称
     */
    @Column(length = 50)
    private String name;
    /**
     * 商品型号
     */
    @Column(length = 50)
    private String model;
    /**
     * 商品类别
     */
    @ManyToOne
    @JoinColumn(name = "typeId")
    private GoodsType type;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 商品单位
     */
    @Column(length = 10)
    private String unit;
    /**
     * 单价
     */
    private float price;
    /**
     * 数量
     */
    private float num;
    /**
     * 总金额
     */
    private float total;
}
