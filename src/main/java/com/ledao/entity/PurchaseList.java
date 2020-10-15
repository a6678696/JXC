package com.ledao.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 进货单实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:27
 */

@Entity
@Table(name = "t_purchase_list")
public class PurchaseList {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 进货单号
     */
    @Column(length = 100)
    private String purchaseNumber;
    /**
     * 供应商
     */
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    /**
     * 进货日期
     */
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
    /**
     * 应付金额
     */
    private float amountPayable;
    /**
     * 实付金额
     */
    private float amountPaid;
    /**
     * 交易状态 1 已付 2 未付
     */
    private Integer state;
    /**
     * 操作用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * 备注
     */
    @Column(length = 1000)
    private String remarks;
    /**
     * 起始日期 搜索用到
     */
    @Transient
    private Date bPurchaseDate;
    /**
     * 结束日期 搜索用到
     */
    @Transient
    private Date ePurchaseDate;
    /**
     * 采购单商品集合
     */
    @Transient
    private List<PurchaseListGoods> purchaseListGoodsList = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public float getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(float amountPayable) {
        this.amountPayable = amountPayable;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getbPurchaseDate() {
        return bPurchaseDate;
    }

    public void setbPurchaseDate(Date bPurchaseDate) {
        this.bPurchaseDate = bPurchaseDate;
    }

    public Date getePurchaseDate() {
        return ePurchaseDate;
    }

    public void setePurchaseDate(Date ePurchaseDate) {
        this.ePurchaseDate = ePurchaseDate;
    }

    public List<PurchaseListGoods> getPurchaseListGoodsList() {
        return purchaseListGoodsList;
    }

    public void setPurchaseListGoodsList(List<PurchaseListGoods> purchaseListGoodsList) {
        this.purchaseListGoodsList = purchaseListGoodsList;
    }

    @Override
    public String toString() {
        return "--{" +
                "编号=" + id +
                ", 进货单号='" + purchaseNumber + '\'' +
                ", 供应商=" + supplier.getName() +
                ", 应付金额=" + amountPayable +
                ", 实付金额=" + amountPaid +
                '}';
    }
}
