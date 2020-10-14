package com.ledao.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ledao.entity.Log;
import com.ledao.entity.SaleCount;
import com.ledao.entity.SaleList;
import com.ledao.entity.SaleListGoods;
import com.ledao.service.LogService;
import com.ledao.service.SaleListGoodsService;
import com.ledao.service.SaleListService;
import com.ledao.service.UserService;
import com.ledao.util.DateUtil;
import com.ledao.util.MathUtil;
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
import java.util.*;

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
        logService.save(new Log(Log.ADD_ACTION, "添加销售单" + saleListService.findById(saleList.getId())));
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

    /**
     * 更新销售单
     *
     * @param id
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions(value = "客户统计")
    public Map<String, Object> update(Integer id) {
        Map<String, Object> resultMap = new HashMap<>(16);
        SaleList saleList = saleListService.findById(id);
        saleList.setState(1);
        saleListService.update(saleList);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 根据条件获取商品销售信息
     *
     * @param saleList
     * @param saleListGoods
     * @return
     */
    @RequestMapping("/listCount")
    @RequiresPermissions(value = "商品销售统计")
    public Map<String, Object> listCount(SaleList saleList, SaleListGoods saleListGoods) {
        Map<String, Object> resultMap = new HashMap<>(16);
        saleListGoods.setTypeId(saleListGoods.getType().getId());
        List<SaleList> SaleListList = saleListService.list(saleList);
        for (SaleList list : SaleListList) {
            saleListGoods.setSaleList(list);
            List<SaleListGoods> SaleListGoodsList = saleListGoodsService.list(saleListGoods);
            list.setSaleListGoodsList(SaleListGoodsList);
        }
        resultMap.put("rows", SaleListList);
        logService.save(new Log(Log.SEARCH_ACTION, "商品采购统计查询"));
        return resultMap;
    }

    /**
     * 按日统计分析
     *
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    @RequestMapping("/countSaleByDay")
    @RequiresPermissions(value = "按日统计分析")
    public Map<String, Object> countSaleByDay(String begin, String end) throws Exception {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<SaleCount> scList = new ArrayList<>();
        List<String> dates = DateUtil.getRangeDates(begin, end);
        List<Object> ll = saleListService.countSaleByDay(begin, end);
        for (String date : dates) {
            SaleCount sc = new SaleCount();
            sc.setDate(date);
            boolean flag = false;
            for (Object o : ll) {
                Object[] oo = (Object[]) o;
                String dd = oo[2].toString().substring(0, 10);
                // 存在
                if (dd.equals(date)) {
                    // 成本总金额
                    sc.setAmountCost(MathUtil.format2Bit(Float.parseFloat(oo[0].toString())));
                    // 销售总金额
                    sc.setAmountSale(MathUtil.format2Bit(Float.parseFloat(oo[1].toString())));
                    // 销售利润
                    sc.setAmountProfit(MathUtil.format2Bit(sc.getAmountSale() - sc.getAmountCost()));
                    flag = true;
                }
            }
            if (!flag) {
                sc.setAmountCost(0);
                sc.setAmountSale(0);
                sc.setAmountProfit(0);
            }
            scList.add(sc);
        }
        resultMap.put("rows", scList);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 按月统计分析
     *
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    @RequestMapping("/countSaleByMonth")
    @RequiresPermissions(value = "按月统计分析")
    public Map<String, Object> countSaleByMonth(String begin, String end) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<SaleCount> scList = new ArrayList<>();
        List<String> dates = DateUtil.getRangeMonths(begin, end);
        List<Object> ll = saleListService.countSaleByMonth(begin, end);
        for (String date : dates) {
            SaleCount sc = new SaleCount();
            sc.setDate(date);
            boolean flag = false;
            for (Object o : ll) {
                Object[] oo = (Object[]) o;
                String dd = oo[2].toString().substring(0, 7);
                // 存在
                if (dd.equals(date)) {
                    // 成本总金额
                    sc.setAmountCost(MathUtil.format2Bit(Float.parseFloat(oo[0].toString())));
                    // 销售总金额
                    sc.setAmountSale(MathUtil.format2Bit(Float.parseFloat(oo[1].toString())));
                    // 销售利润
                    sc.setAmountProfit(MathUtil.format2Bit(sc.getAmountSale() - sc.getAmountCost()));
                    flag = true;
                }
            }
            if (!flag) {
                sc.setAmountCost(0);
                sc.setAmountSale(0);
                sc.setAmountProfit(0);
            }
            scList.add(sc);
        }
        resultMap.put("rows", scList);
        resultMap.put("success", true);
        return resultMap;
    }
}
