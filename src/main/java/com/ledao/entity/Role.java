package com.ledao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色实体
 *
 * @author LeDao
 * @company
 * @create 2020-01-06 20:16
 */
@Data
@Entity
@Table(name="t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1908576881377362453L;

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 角色名称
     */
    @Column(length=50)
    private String name;
    /**
     * 备注
     */
    @Column(length=1000)
    private String remarks;
}
