package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * 用户实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-06 20:14
 */
@Data
@Entity
@Table(name = "t_user")
public class User {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @NotEmpty(message = "请输入用户名!")
    @Column(length=50)
    private String userName;

    /**
     * 密码
     */
    @NotEmpty(message = "请输入密码!")
    @Column(length=50)
    private String password;

    /**
     * 真实姓名
     */
    @Column(length=50)
    private String trueName;

    /**
     * 备注
     */
    @Column(length=1000)
    private String remarks;
}
