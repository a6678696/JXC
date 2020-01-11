package com.ledao.service.impl;

import com.ledao.entity.Log;
import com.ledao.repository.LogRepository;
import com.ledao.repository.UserRepository;
import com.ledao.service.LogService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 系统日志service实现类
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 11:51
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    @Resource
    private LogRepository logRepository;

    @Resource
    private UserRepository userRepository;

    /**
     * 添加或者修改日志信息
     *
     * @param log
     */
    @Override
    public void save(Log log) {
        //设置操作日期
        log.setTime(new Date());
        //设置当前用户
        log.setUser(userRepository.findByUserName((String) SecurityUtils.getSubject().getPrincipal()));
        logRepository.save(log);
    }
}
