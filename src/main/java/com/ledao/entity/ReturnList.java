package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 退货单实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:27
 */
@Data
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
}
