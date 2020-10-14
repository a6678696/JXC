package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 客户退货单商品实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:35
 */
@Data
@Entity
@Table(name="t_customer_return_list_goods")
public class CustomerReturnListGoods {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户退货单
     */
    @ManyToOne
    @JoinColumn(name="customer_return_list_id")
    private CustomerReturnList customerReturnList;
    /**
     * 商品编码
     */
    @Column(length=50)
    private String code;
    /**
     * 商品名称
     */
    @Column(length=50)
    private String name;
    /**
     * 商品型号
     */
    @Column(length=50)
    private String model;
    /**
     * 商品类别
     */
    @ManyToOne
    @JoinColumn(name="type_id")
    private GoodsType type;
    /**
     * 类别id
     */
    @Transient
    private Integer typeId;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 商品单位
     */
    @Column(length=10)
    private String unit;
    /**
     * 单价
     */
    private float price;
    /**
     * 数量
     */
    private int num;
    /**
     * 总金额
     */
    private float total;
    /**
     * 编号或名称
     */
    @Transient
    private String codeOrName;
}
