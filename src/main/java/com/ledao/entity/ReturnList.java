package com.ledao.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 退货单实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:27
 */

@Entity
@Table(name = "t_return_list")
public class ReturnList {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 退货单号
     */
    @Column(length = 100)
    private String returnNumber;
    /**
     * 供应商
     */
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    /**
     * 退货日期
     */
    @Temporal(TemporalType.DATE)
    private Date returnDate;
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
    private Date bReturnDate;
    /**
     * 结束日期 搜索用到
     */
    @Transient
    private Date eReturnDate;
    /**
     * 退货单商品集合
     */
    @Transient
    List<ReturnListGoods> ReturnListGoodsList = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
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

    public Date getbReturnDate() {
        return bReturnDate;
    }

    public void setbReturnDate(Date bReturnDate) {
        this.bReturnDate = bReturnDate;
    }

    public Date geteReturnDate() {
        return eReturnDate;
    }

    public void seteReturnDate(Date eReturnDate) {
        this.eReturnDate = eReturnDate;
    }

    public List<ReturnListGoods> getReturnListGoodsList() {
        return ReturnListGoodsList;
    }

    public void setReturnListGoodsList(List<ReturnListGoods> returnListGoodsList) {
        ReturnListGoodsList = returnListGoodsList;
    }

    @Override
    public String toString() {
        return "--{" +
                "编号=" + id +
                ", 退货单号='" + returnNumber + '\'' +
                ", 供应商=" + supplier +
                ", 应付金额=" + amountPayable +
                ", 实付金额=" + amountPaid +
                '}';
    }
}
