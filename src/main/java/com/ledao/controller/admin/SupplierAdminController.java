package com.ledao.controller.admin;

import com.ledao.entity.Log;
import com.ledao.entity.Supplier;
import com.ledao.service.LogService;
import com.ledao.service.SupplierService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理供应商Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-11 21:24
 */
@RestController
@RequestMapping("/admin/supplier")
public class SupplierAdminController {

    @Resource
    private SupplierService supplierService;

    @Resource
    private LogService logService;

    /**
     * 下拉框模糊查询
     *
     * @param q
     * @return
     */
    @RequestMapping("/comboList")
    @RequiresPermissions(value = "进货入库")
    public List<Supplier> comboList(String q) {
        if (q == null) {
            q = "";
        }
        return supplierService.findByName("%" + q + "%");
    }

    /**
     * 分页查询供应商信息
     *
     * @param searchSupplier
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = "供应商管理")
    public Map<String, Object> list(Supplier searchSupplier, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<Supplier> supplierList = supplierService.list(searchSupplier, page, rows);
        Long total = supplierService.getCount(searchSupplier);
        resultMap.put("rows", supplierList);
        resultMap.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION, "查询供应商信息"));
        return resultMap;
    }

    /**
     * 添加或者修改供应商信息
     *
     * @param supplier
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "供应商管理")
    public Map<String, Object> save(Supplier supplier) {
        Map<String, Object> resultMap = new HashMap<>(16);
        if (supplier.getId() != null) {
            logService.save(new Log(Log.UPDATE_ACTION, "更新供应商信息" + supplier));
        } else {
            logService.save(new Log(Log.ADD_ACTION, "添加供应商信息" + supplier));
        }
        supplierService.save(supplier);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 删除供应商信息
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = "供应商管理")
    public Map<String, Object> delete(String ids) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            logService.save(new Log(Log.DELETE_ACTION, "删除供应商信息" + supplierService.findById(Integer.parseInt(idsStr[i]))));
            supplierService.delete(Integer.parseInt(idsStr[i]));
        }
        resultMap.put("success", true);
        return resultMap;
    }
}
