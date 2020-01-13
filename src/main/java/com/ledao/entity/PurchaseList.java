package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 进货单实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:27
 */
@Data
@Entity
@Table(name = "t_purchaseList")
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
    @JoinColumn(name = "supplierId")
    private Supplier supplier;
    /**
     * 进货日期
     */
    @Temporal(TemporalType.TIMESTAMP)
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
    @JoinColumn(name = "userId")
    private User user;
    /**
     * 备注
     */
    @Column(length = 1000)
    private String remarks;
}
