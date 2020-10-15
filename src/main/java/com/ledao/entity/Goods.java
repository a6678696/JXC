package com.ledao.entity;

import javax.persistence.*;

/**
 * 商品实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 10:11
 */

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
    /**
     * 查询用到 根据商品编码或者商品名称查询
     */
    @Transient
    private String codeOrName;
    /**
     * 销售总数
     */
    @Transient
    private Integer saleTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getLastPurchasingPrice() {
        return lastPurchasingPrice;
    }

    public void setLastPurchasingPrice(float lastPurchasingPrice) {
        this.lastPurchasingPrice = lastPurchasingPrice;
    }

    public float getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(float purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public Integer getMinNum() {
        return minNum;
    }

    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCodeOrName() {
        return codeOrName;
    }

    public void setCodeOrName(String codeOrName) {
        this.codeOrName = codeOrName;
    }

    public Integer getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(Integer saleTotal) {
        this.saleTotal = saleTotal;
    }

    @Override
    public String toString() {
        return "--{" +
                "id=" + id +
                ", 编号='" + code + '\'' +
                ", 商品名称='" + name + '\'' +
                ", 采购价格=" + purchasingPrice +
                ", 出售价格=" + sellingPrice +
                ", 生产厂商=" + producer +
                '}';
    }
}
