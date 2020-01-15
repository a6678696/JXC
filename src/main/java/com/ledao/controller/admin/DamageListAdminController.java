package com.ledao.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ledao.entity.Log;
import com.ledao.entity.DamageList;
import com.ledao.entity.DamageListGoods;
import com.ledao.service.LogService;
import com.ledao.service.DamageListGoodsService;
import com.ledao.service.DamageListService;
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
 * 后台管理商品报损单Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:45
 */
@RestController
@RequestMapping("/admin/damageList")
public class DamageListAdminController {

    @Resource
    private DamageListService damageListService;

    @Resource
    private DamageListGoodsService damageListGoodsService;

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
     * 获取商品报损单号
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/genCode")
    @RequiresPermissions(value = "商品报损")
    public String genCode() throws Exception {
        StringBuffer code = new StringBuffer("JH");
        code.append(DateUtil.getCurrentDateStr());
        String damageNumber = damageListService.getTodayMaxDamageNumber();
        if (damageNumber != null) {
            code.append(StringUtil.formatCode(damageNumber));
        } else {
            code.append("0001");
        }
        return code.toString();
    }

    /**
     * 添加商品报损单 以及所有商品报损单商品
     *
     * @param damageList
     * @param goodsJson
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "商品报损")
    public Map<String, Object> save(DamageList damageList, String goodsJson) {
        Map<String, Object> resultMap = new HashMap<>(16);
        //设置操作用户
        damageList.setUser(userService.findByUserName(SecurityUtils.getSubject().getPrincipal().toString()));
        Gson gson = new Gson();
        List<DamageListGoods> damageListGoodsList = gson.fromJson(goodsJson, new TypeToken<List<DamageListGoods>>() {
        }.getType());
        damageListService.save(damageList, damageListGoodsList);
        logService.save(new Log(Log.ADD_ACTION, "添加商品报损单"));
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 根据条件查询所有商品报损单信息
     *
     * @param searchDamageList
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = "报损报溢查询")
    public Map<String, Object> list(DamageList searchDamageList, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<DamageList> damageListList = damageListService.list(searchDamageList);
        resultMap.put("rows", damageListList);
        logService.save(new Log(Log.SEARCH_ACTION, "商品报损单查询"));
        return resultMap;
    }

    /**
     * 根据商品报损单id查询所有商品报损单商品
     *
     * @param damageListId
     * @return
     */
    @RequestMapping("/listGoods")
    @RequiresPermissions(value = "报损报溢查询")
    public Map<String, Object> listGoods(Integer damageListId) {
        if (damageListId == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("rows", damageListGoodsService.listByDamageListId(damageListId));
        logService.save(new Log(Log.SEARCH_ACTION, "商品报损单商品查询"));
        return resultMap;
    }
}
