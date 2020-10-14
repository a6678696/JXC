package com.ledao.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ledao.entity.*;
import com.ledao.service.CustomerReturnListGoodsService;
import com.ledao.service.CustomerReturnListService;
import com.ledao.service.LogService;
import com.ledao.service.UserService;
import com.ledao.util.DateUtil;
import com.ledao.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理客户退货单Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-15 15:03
 */
@RestController
@RequestMapping("/admin/customerReturnList")
public class CustomerReturnListAdminController {

    @Resource
    private CustomerReturnListService customerReturnListService;

    @Resource
    private CustomerReturnListGoodsService customerReturnListGoodsService;

    @Resource
    private UserService userService;

    @Resource
    private LogService logService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        //true:允许输入空值，false:不能为空值
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 获取客户退货单号
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/genCode")
    @RequiresPermissions(value = "客户退货")
    public String genCode() throws Exception {
        StringBuffer code = new StringBuffer("XT");
        code.append(DateUtil.getCurrentDateStr());
        String customerReturnNumber = customerReturnListService.getTodayMaxCustomerReturnNumber();
        if (customerReturnNumber != null) {
            code.append(StringUtil.formatCode(customerReturnNumber));
        } else {
            code.append("0001");
        }
        return code.toString();
    }

    /**
     * 添加客户退货单 以及所有客户退货单商品
     *
     * @param customerReturnList
     * @param goodsJson
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "客户退货")
    public Map<String, Object> save(CustomerReturnList customerReturnList, String goodsJson) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        // 设置操作用户
        customerReturnList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal()));
        Gson gson = new Gson();
        List<CustomerReturnListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<CustomerReturnListGoods>>() {
        }.getType());
        customerReturnListService.save(customerReturnList, plgList);
        logService.save(new Log(Log.ADD_ACTION, "添加客户退货单" + customerReturnListService.findById(customerReturnList.getId())));
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 根据条件查询所有客户退货单信息
     *
     * @param customerReturnList
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = "客户退货查询")
    public Map<String, Object> list(CustomerReturnList customerReturnList) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<CustomerReturnList> customerReturnListList = customerReturnListService.list(customerReturnList);
        resultMap.put("rows", customerReturnListList);
        logService.save(new Log(Log.SEARCH_ACTION, "客户退货单查询"));
        return resultMap;
    }

    /**
     * 根据客户退货单id查询所有客户退货单商品
     *
     * @param customerReturnListId
     * @return
     * @throws Exception
     */
    @RequestMapping("/listGoods")
    @RequiresPermissions(value = "客户退货查询")
    public Map<String, Object> listGoods(Integer customerReturnListId) throws Exception {
        if (customerReturnListId == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("rows", customerReturnListGoodsService.listByCustomerReturnListId(customerReturnListId));
        logService.save(new Log(Log.SEARCH_ACTION, "客户退货单商品查询"));
        return resultMap;
    }

    /**
     * 删除客户退货单 以及客户退货单里的商品
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = "客户退货查询")
    public Map<String, Object> delete(Integer id) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        logService.save(new Log(Log.DELETE_ACTION, "删除客户退货单信息：" + customerReturnListService.findById(id)));
        customerReturnListService.delete(id);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 更新客户退货单
     *
     * @param id
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions(value = "客户统计")
    public Map<String, Object> update(Integer id) {
        Map<String, Object> resultMap = new HashMap<>(16);
        CustomerReturnList customerReturnList = customerReturnListService.findById(id);
        customerReturnList.setState(1);
        customerReturnListService.update(customerReturnList);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 根据条件获取商品销售信息
     *
     * @param customerReturnList
     * @param customerReturnListGoods
     * @return
     */
    @RequestMapping("/listCount")
    @RequiresPermissions(value = "商品销售统计")
    public Map<String, Object> listCount(CustomerReturnList customerReturnList, CustomerReturnListGoods customerReturnListGoods) {
        Map<String, Object> resultMap = new HashMap<>(16);
        customerReturnListGoods.setTypeId(customerReturnListGoods.getType().getId());
        List<CustomerReturnList> CustomerReturnListList = customerReturnListService.list(customerReturnList);
        for (CustomerReturnList list : CustomerReturnListList) {
            customerReturnListGoods.setCustomerReturnList(list);
            List<CustomerReturnListGoods> CustomerReturnListGoodsList = customerReturnListGoodsService.list(customerReturnListGoods);
            list.setCustomerReturnListGoodsList(CustomerReturnListGoodsList);
        }
        resultMap.put("rows", CustomerReturnListList);
        logService.save(new Log(Log.SEARCH_ACTION, "商品采购统计查询"));
        return resultMap;
    }
}
