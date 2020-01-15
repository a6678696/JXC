package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品报损单实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:27
 */
@Data
@Entity
@Table(name = "t_damage_list")
public class DamageList {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 商品报损单号
     */
    @Column(length = 100)
    private String damageNumber;
    /**
     * 商品报损日期
     */
    @Temporal(TemporalType.DATE)
    private Date damageDate;
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
    private Date bDamageDate;
    /**
     * 结束日期 搜索用到
     */
    @Transient
    private Date eDamageDate;

    @Override
    public String toString() {
        return "--{" +
                "编号=" + id +
                ", 商品报损单号='" + damageNumber + '\'' +
                ", 商品报损日期=" + damageDate +
                ", 操作用户=" + user.getTrueName() +
                ", 备注='" + remarks + '\'' +
                '}';
    }
}
