package com.ledao.service;

import com.ledao.entity.Log;

/**
 * 日志Service接口
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 11:50
 */
public interface LogService {
    /**
     * 添加或者修改日志信息
     *
     * @param log
     */
    void save(Log log);
}
