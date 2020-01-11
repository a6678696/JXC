package com.ledao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**系统日志实体
 * @author LeDao
 * @company
 * @create 2020-01-11 10:58
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "t_log")
public class Log {

    public final static String LOGIN_ACTION = "登录操作";
    public final static String LOGOUT_ACTION = "登录操作";
    public final static String ADD_ACTION = "添加操作";
    public final static String DELETE_ACTION = "删除操作";
    public final static String UPDATE_ACTION = "更新操作";
    public final static String SEARCH_ACTION = "查询操作";

    /**
     * 编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 日志类型
     */
    @Column(length = 100)
    private String type;
    /**
     * 操作用户
     */
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    /**
     * 操作内容
     */
    @Column(length = 100)
    private String content;
    /**
     * 操作日期时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Log(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public Log() {

    }
}
