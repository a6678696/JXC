package com.ledao.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 菜单实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-06 20:17
 */

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
     * 菜单名称
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



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
