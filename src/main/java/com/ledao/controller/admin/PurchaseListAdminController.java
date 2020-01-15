package com.ledao.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ledao.entity.Log;
import com.ledao.entity.PurchaseList;
import com.ledao.entity.PurchaseListGoods;
import com.ledao.service.LogService;
import com.ledao.service.PurchaseListGoodsService;
import com.ledao.service.PurchaseListService;
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
 * 后台管理进货单Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:45
 */
@RestController
@RequestMapping("/admin/purchaseList")
public class PurchaseListAdminController {

    @Resource
    private PurchaseListService purchaseListService;

    @Resource
    private PurchaseListGoodsService purchaseListGoodsService;

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
     * 获取进货单号
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/genCode")
    @RequiresPermissions(value = "进货入库")
    public String genCode() throws Exception {
        StringBuffer code = new StringBuffer("JH");
        code.append(DateUtil.getCurrentDateStr());
        String purchaseNumber = purchaseListService.getTodayMaxPurchaseNumber();
        if (purchaseNumber != null) {
            code.append(StringUtil.formatCode(purchaseNumber));
        } else {
            code.append("0001");
        }
        return code.toString();
    }

    /**
     * 添加进货单 以及所有进货单商品
     *
     * @param purchaseList
     * @param goodsJson
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "进货入库")
    public Map<String, Object> save(PurchaseList purchaseList, String goodsJson) {
        Map<String, Object> resultMap = new HashMap<>(16);
        //设置操作用户
        purchaseList.setUser(userService.findByUserName(SecurityUtils.getSubject().getPrincipal().toString()));
        Gson gson = new Gson();
        List<PurchaseListGoods> purchaseListGoodsList = gson.fromJson(goodsJson, new TypeToken<List<PurchaseListGoods>>() {
        }.getType());
        purchaseListService.save(purchaseList, purchaseListGoodsList);
        logService.save(new Log(Log.ADD_ACTION, "添加进货单"));
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 根据条件查询所有进货单信息
     *
     * @param searchPurchaseList
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = "进货单据查询")
    public Map<String, Object> list(PurchaseList searchPurchaseList, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<PurchaseList> purchaseListList = purchaseListService.list(searchPurchaseList);
        resultMap.put("rows", purchaseListList);
        logService.save(new Log(Log.SEARCH_ACTION, "进货单查询"));
        return resultMap;
    }

    /**
     * 根据进货单id查询所有进货单商品
     *
     * @param purchaseListId
     * @return
     */
    @RequestMapping("/listGoods")
    @RequiresPermissions(value = "进货单据查询")
    public Map<String, Object> listGoods(Integer purchaseListId) {
        if (purchaseListId == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("rows", purchaseListGoodsService.listByPurchaseListId(purchaseListId));
        logService.save(new Log(Log.SEARCH_ACTION, "进货单商品查询"));
        return resultMap;
    }

    /**
     * 删除进货单 以及进货单里的商品
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = "进货单据查询")
    public Map<String, Object> delete(Integer id) {
        Map<String, Object> resultMap = new HashMap<>(16);
        logService.save(new Log(Log.DELETE_ACTION, "删除进货单信息" + purchaseListService.findById(id)));
        purchaseListService.delete(id);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 修改进货单的支付状态
     *
     * @param id
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions(value = "供应商统计")
    public Map<String, Object> update(Integer id) {
        Map<String, Object> resultMap = new HashMap<>(16);
        PurchaseList purchaseList = purchaseListService.findById(id);
        purchaseList.setState(1);
        purchaseListService.update(purchaseList);
        resultMap.put("success", true);
        return resultMap;
    }
}
