package com.ledao.entity;

import javax.persistence.*;

/**
 * 商品报溢单商品实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:35
 */

@Entity
@Table(name = "t_overflow_list_goods")
public class OverflowListGoods {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 商品报溢单
     */
    @ManyToOne
    @JoinColumn(name = "overflow_list_id")
    private OverflowList overflowList;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OverflowList getOverflowList() {
        return overflowList;
    }

    public void setOverflowList(OverflowList overflowList) {
        this.overflowList = overflowList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
