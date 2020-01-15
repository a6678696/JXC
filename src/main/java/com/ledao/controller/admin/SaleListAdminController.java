package com.ledao.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ledao.entity.Log;
import com.ledao.entity.SaleList;
import com.ledao.entity.SaleListGoods;
import com.ledao.service.LogService;
import com.ledao.service.SaleListGoodsService;
import com.ledao.service.SaleListService;
import com.ledao.service.UserService;
import com.ledao.util.DateUtil;
import com.ledao.util.StringUtil;
import org.apache.shiro.SecurityUtils;
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
 * 后台管理销售单Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:45
 */
@RestController
@RequestMapping("/admin/saleList")
public class SaleListAdminController {

    @Resource
    private SaleListService saleListService;

    @Resource
    private SaleListGoodsService saleListGoodsService;

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
     * 获取销售单号
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/genCode")
    @RequiresPermissions(value = "销售出库")
    public String genCode() throws Exception {
        StringBuffer code = new StringBuffer("XS");
        code.append(DateUtil.getCurrentDateStr());
        String saleNumber = saleListService.getTodayMaxSaleNumber();
        if (saleNumber != null) {
            code.append(StringUtil.formatCode(saleNumber));
        } else {
            code.append("0001");
        }
        return code.toString();
    }

    /**
     * 添加销售单 以及所有销售单商品
     *
     * @param saleList
     * @param goodsJson
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "销售出库")
    public Map<String, Object> save(SaleList saleList, String goodsJson) {
        Map<String, Object> resultMap = new HashMap<>(16);
        //设置操作用户
        saleList.setUser(userService.findByUserName(SecurityUtils.getSubject().getPrincipal().toString()));
        Gson gson = new Gson();
        List<SaleListGoods> saleListGoodsList = gson.fromJson(goodsJson, new TypeToken<List<SaleListGoods>>() {
        }.getType());
        saleListService.save(saleList, saleListGoodsList);
        logService.save(new Log(Log.ADD_ACTION, "添加销售单"+saleListService.findById(saleList.getId())));
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 根据条件查询所有销售单信息
     *
     * @param searchSaleList
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = "销售单据查询")
    public Map<String, Object> list(SaleList searchSaleList, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<SaleList> saleListList = saleListService.list(searchSaleList);
        resultMap.put("rows", saleListList);
        logService.save(new Log(Log.SEARCH_ACTION, "销售单查询"));
        return resultMap;
    }

    /**
     * 根据销售单id查询所有销售单商品
     *
     * @param saleListId
     * @return
     */
    @RequestMapping("/listGoods")
    @RequiresPermissions(value = "销售单据查询")
    public Map<String, Object> listGoods(Integer saleListId) {
        if (saleListId == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("rows", saleListGoodsService.listBySaleListId(saleListId));
        logService.save(new Log(Log.SEARCH_ACTION, "销售单商品查询"));
        return resultMap;
    }

    /**
     * 删除销售单 以及销售单里的商品
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = "销售单据查询")
    public Map<String, Object> delete(Integer id) {
        Map<String, Object> resultMap = new HashMap<>(16);
        logService.save(new Log(Log.DELETE_ACTION, "删除销售单信息" + saleListService.findById(id)));
        saleListService.delete(id);
        resultMap.put("success", true);
        return resultMap;
    }
}
