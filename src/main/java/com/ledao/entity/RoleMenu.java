package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 角色菜单关联实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-06 20:20
 */
@Data
@Entity
@Table(name = "t_roleMenu")
public class RoleMenu {

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 角色
     */
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
    /**
     * 菜单
     */
    @ManyToOne
    @JoinColumn(name = "menuId")
    private Menu menu;
}
