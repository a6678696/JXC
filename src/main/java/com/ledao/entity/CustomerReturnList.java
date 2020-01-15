package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户退货单实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:27
 */
@Data
@Entity
@Table(name="t_customer_return_list")
public class CustomerReturnList {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户退货单号
     */
    @Column(length=100)
    private String customerReturnNumber;

    /**
     * 客户
     */
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    /**
     * 客户退货日期
     */
    @Temporal(TemporalType.DATE)
    private Date customerReturnDate;

    /**
     *  起始日期 搜索用到
     */
    @Transient
    private Date bCustomerReturnDate;

    /**
     * 结束日期 搜索用到
     */
    @Transient
    private Date eCustomerReturnDate;

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
     * 操作员
     */
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    /**
     * 备注
     */
    @Column(length=1000)
    private String remarks;

    @Override
    public String toString() {
        return "--{" +
                "编号=" + id +
                ", 客户退货单号='" + customerReturnNumber + '\'' +
                ", 客户=" + customer.getName() +
                ", 应付金额=" + amountPayable +
                ", 实付金额=" + amountPaid +
                '}';
    }
}
