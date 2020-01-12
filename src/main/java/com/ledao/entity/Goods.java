package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 10:11
 */
@Data
@Entity
@Table(name = "t_goods")
public class Goods {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
    @JoinColumn(name = "type_id")
    private GoodsType type;
    /**
     * 商品单位
     */
    @Column(length=10)
    private String unit;
    /**
     * 上次采购价格
     */
    private float lastPurchasingPrice;
    /**
     * 采购价格  成本价  假如价格变动 算平均值
     */
    private float purchasingPrice;
    /**
     * 出售价格
     */
    private float sellingPrice;
    /**
     * 库存数量
     */
    private Integer inventoryQuantity;
    /**
     * 库存下限
     */
    private Integer minNum;
    /**
     * 0 初始化状态 1 期初库存入仓库  2  有进货或者销售单据
     */
    private Integer state;
    /**
     * 生产厂商
     */
    @Column(length=200)
    private String producer;
    /**
     * 备注
     */
    @Column(length=1000)
    private String remarks;

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", purchasingPrice=" + purchasingPrice +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}
