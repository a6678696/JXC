package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品报损单商品实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:35
 */
@Data
@Entity
@Table(name = "t_damage_list_goods")
public class DamageListGoods {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 商品报损单
     */
    @ManyToOne
    @JoinColumn(name = "damage_list_id")
    private DamageList damageList;
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
    @JoinColumn(name = "type_id")
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
    @Column(length = 10)
    private String unit;
    /**
     * 单价
     */
    private float price;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 总金额
     */
    private float total;
}
