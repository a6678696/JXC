package com.ledao.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ledao.entity.OverflowList;
import com.ledao.entity.OverflowListGoods;
import com.ledao.entity.Log;
import com.ledao.service.OverflowListGoodsService;
import com.ledao.service.OverflowListService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理商品报溢单Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:45
 */
@RestController
@RequestMapping("/admin/overflowList")
public class OverflowListAdminController {

    @Resource
    private OverflowListService overflowListService;

    @Resource
    private OverflowListGoodsService overflowListGoodsService;

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
     * 获取商品报溢单号
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/genCode")
    @RequiresPermissions(value = "商品报溢")
    public String genCode() throws Exception {
        StringBuffer code = new StringBuffer("JH");
        code.append(DateUtil.getCurrentDateStr());
        String overflowNumber = overflowListService.getTodayMaxOverflowNumber();
        if (overflowNumber != null) {
            code.append(StringUtil.formatCode(overflowNumber));
        } else {
            code.append("0001");
        }
        return code.toString();
    }

    /**
     * 添加商品报溢单 以及所有商品报溢单商品
     *
     * @param overflowList
     * @param goodsJson
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "商品报溢")
    public Map<String, Object> save(OverflowList overflowList, String goodsJson) {
        Map<String, Object> resultMap = new HashMap<>(16);
        //设置操作用户
        overflowList.setUser(userService.findByUserName(SecurityUtils.getSubject().getPrincipal().toString()));
        Gson gson = new Gson();
        List<OverflowListGoods> overflowListGoodsList = gson.fromJson(goodsJson, new TypeToken<List<OverflowListGoods>>() {
        }.getType());
        overflowListService.save(overflowList, overflowListGoodsList);
        logService.save(new Log(Log.ADD_ACTION, "添加商品报溢单"+overflowListService.findById(overflowList.getId())));
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 根据条件查询所有商品报溢单信息
     *
     * @param searchOverflowList
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = "报损报溢查询")
    public Map<String, Object> list(OverflowList searchOverflowList, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<OverflowList> overflowListList = overflowListService.list(searchOverflowList);
        resultMap.put("rows", overflowListList);
        logService.save(new Log(Log.SEARCH_ACTION, "商品报溢单查询"));
        return resultMap;
    }

    /**
     * 根据商品报溢单id查询所有商品报溢单商品
     *
     * @param overflowListId
     * @return
     */
    @RequestMapping("/listGoods")
    @RequiresPermissions(value = "报损报溢查询")
    public Map<String, Object> listGoods(Integer overflowListId) {
        if (overflowListId == null) {
            return null;
        }
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("rows", overflowListGoodsService.listByOverflowListId(overflowListId));
        logService.save(new Log(Log.SEARCH_ACTION, "商品报溢单商品查询"));
        return resultMap;
    }
}
