package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 菜单实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-06 20:17
 */
@Data
@Entity
@Table(name = "t_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = -8628636035214932649L;

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 菜名名称
     */
    @Column(length = 50)
    private String name;
    /**
     * 菜单地址
     */
    @Column(length = 200)
    private String url;
    /**
     * 菜单节点类型 1 根节点 0 叶子节点
     */
    private Integer state;
    /**
     * 图标
     */
    @Column(length = 100)
    private String icon;
    /**
     * 父菜单Id
     */
    private Integer pId;
}
