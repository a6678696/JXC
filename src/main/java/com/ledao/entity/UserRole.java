package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户角色关联实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-06 20:19
 */
@Data
@Entity
@Table(name = "t_userRole")
public class UserRole {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    /**
     * 角色
     */
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
}
