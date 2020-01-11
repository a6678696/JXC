package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色关联实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-06 20:19
 */
@Data
@Entity
@Table(name = "t_user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -7161756798700931249L;

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
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 角色
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
