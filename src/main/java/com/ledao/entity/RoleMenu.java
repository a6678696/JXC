package com.ledao.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色菜单关联实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-06 20:20
 */

@Entity
@Table(name = "t_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 3476991653490999782L;

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
    @JoinColumn(name = "role_id")
    private Role role;
    /**
     * 菜单
     */
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
