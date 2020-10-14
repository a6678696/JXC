package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 销售单实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:27
 */
@Data
@Entity
@Table(name = "t_sale_list")
public class SaleList {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 销售单号
     */
    @Column(length = 100)
    private String saleNumber;
    /**
     * 客户
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    /**
     * 销售日期
     */
    @Temporal(TemporalType.DATE)
    private Date saleDate;
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
    private Date bSaleDate;
    /**
     * 结束日期 搜索用到
     */
    @Transient
    private Date eSaleDate;
    /**
     * 销售单商品集合
     */
    @Transient
    private List<SaleListGoods> saleListGoodsList = null;

    @Override
    public String toString() {
        return "--{" +
                "编号=" + id +
                ", 销售单号='" + saleNumber + '\'' +
                ", 客户=" + customer.getName() +
                ", 应付金额=" + amountPayable +
                ", 实付金额=" + amountPaid +
                '}';
    }
}
