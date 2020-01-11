package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 供应商实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 21:01
 */
@Data
@Entity
@Table(name = "t_supplier")
public class Supplier {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 供应商名称
     */
    @Column(length = 200)
    private String name;
    /**
     * 联系人
     */
    @Column(length = 50)
    private String contact;
    /**
     * 联系电话
     */
    @Column(length = 50)
    private String number;
    /**
     * 联系地址
     */
    @Column(length = 300)
    private String address;
    /**
     * 备注
     */
    @Column(length = 1000)
    private String remarks;

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
