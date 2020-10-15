package com.ledao.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ledao.util.CustomDateTimeSerializer;

import javax.persistence.*;
import java.util.Date;

/**系统日志实体
 * @author LeDao
 * @company
 * @create 2020-01-11 10:58
 */

@Entity
@Table(name = "t_log")
public class Log {

    public final static String LOGIN_ACTION = "登录操作";
    public final static String LOGOUT_ACTION = "注销操作";
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
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * 操作内容
     */
    @Column(length = 1000)
    private String content;
    /**
     * 操作日期时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    /**
     *起始时间 搜索用到
     */
    @Transient
    private Date bTime;

    /**
     * 结束时间 搜索用到
     */
    @Transient
    private Date eTime;

    public Log(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public Log() {

    }


    @JsonSerialize(using= CustomDateTimeSerializer.class)
    public Date getTime() {
        return time;
    }

    public static String getLoginAction() {
        return LOGIN_ACTION;
    }

    public static String getLogoutAction() {
        return LOGOUT_ACTION;
    }

    public static String getAddAction() {
        return ADD_ACTION;
    }

    public static String getDeleteAction() {
        return DELETE_ACTION;
    }

    public static String getUpdateAction() {
        return UPDATE_ACTION;
    }

    public static String getSearchAction() {
        return SEARCH_ACTION;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getbTime() {
        return bTime;
    }

    public void setbTime(Date bTime) {
        this.bTime = bTime;
    }

    public Date geteTime() {
        return eTime;
    }

    public void seteTime(Date eTime) {
        this.eTime = eTime;
    }
}
