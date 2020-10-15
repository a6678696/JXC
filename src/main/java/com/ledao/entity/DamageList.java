package com.ledao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品报损单实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:27
 */

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDamageNumber() {
        return damageNumber;
    }

    public void setDamageNumber(String damageNumber) {
        this.damageNumber = damageNumber;
    }

    public Date getDamageDate() {
        return damageDate;
    }

    public void setDamageDate(Date damageDate) {
        this.damageDate = damageDate;
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

    public Date getbDamageDate() {
        return bDamageDate;
    }

    public void setbDamageDate(Date bDamageDate) {
        this.bDamageDate = bDamageDate;
    }

    public Date geteDamageDate() {
        return eDamageDate;
    }

    public void seteDamageDate(Date eDamageDate) {
        this.eDamageDate = eDamageDate;
    }

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
