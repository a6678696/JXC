package com.ledao.controller.admin;

import com.ledao.entity.Log;
import com.ledao.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统日志Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 12:54
 */
@RestController
@RequestMapping("/admin/log")
public class LogAdminController {

    @Resource
    private LogService logService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(true);
        //true:允许输入空值，false:不能为空值
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 根据条件分页查询日志信息
     *
     * @param searchLog
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = "系统日志")
    public Map<String, Object> list(Log searchLog, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<Log> logList = logService.list(searchLog, page, rows);
        Long total = logService.getCount(searchLog);
        resultMap.put("rows", logList);
        resultMap.put("total", total);
        return resultMap;
    }
}
