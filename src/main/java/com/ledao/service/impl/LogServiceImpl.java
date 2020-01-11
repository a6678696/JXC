package com.ledao.service.impl;

import com.ledao.entity.Log;
import com.ledao.repository.LogRepository;
import com.ledao.repository.UserRepository;
import com.ledao.service.LogService;
import com.ledao.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

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

    /**
     * 根据条件分页查询日志信息
     *
     * @param log
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Log> list(Log log, Integer page, Integer pageSize) {
        Pageable pageable =PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "time");
        Page<Log> logPage=logRepository.findAll(new Specification<Log>() {
            @Override
            public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (log != null) {
                    if(StringUtil.isNotEmpty(log.getType())){
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("type"), log.getType()));
                    }
                    if(log.getUser()!=null && StringUtil.isNotEmpty(log.getUser().getTrueName())){
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("user").get("trueName"), "%"+log.getUser().getTrueName()+"%"));
                    }
                    if(log.getBTime()!=null){
                        predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get("time"), log.getBTime()));
                    }
                    if(log.getETime()!=null){
                        predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get("time"), log.getETime()));
                    }
                }
                return predicate;
            }
        },pageable);
        return logPage.getContent();
    }

    /**
     * 获取总记录数
     *
     * @param log
     * @return
     */
    @Override
    public Long getCount(Log log) {
        Long count=logRepository.count(new Specification<Log>() {

            @Override
            public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate=cb.conjunction();
                if(log!=null){
                    if(StringUtil.isNotEmpty(log.getType())){
                        predicate.getExpressions().add(cb.equal(root.get("type"), log.getType()));
                    }
                    if(log.getUser()!=null && StringUtil.isNotEmpty(log.getUser().getTrueName())){
                        predicate.getExpressions().add(cb.like(root.get("user").get("trueName"), "%"+log.getUser().getTrueName()+"%"));
                    }
                    if(log.getBTime()!=null){
                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("time"), log.getBTime()));
                    }
                    if(log.getETime()!=null){
                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("time"), log.getBTime()));
                    }
                }
                return predicate;
            }

        });
        return count;
    }
}
