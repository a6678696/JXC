package com.ledao.controller.admin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ledao.entity.Log;
import com.ledao.entity.ReturnList;
import com.ledao.entity.ReturnListGoods;
import com.ledao.service.LogService;
import com.ledao.service.ReturnListGoodsService;
import com.ledao.service.ReturnListService;
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
 * 后台管理退货单Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-13 22:45
 */
@RestController
@RequestMapping("/admin/returnList")
public class ReturnListAdminController {

    @Resource
    private ReturnListService returnListService;

    @Resource
    private ReturnListGoodsService returnListGoodsService;

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
     * 获取退货单号
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/genCode")
    @RequiresPermissions(value = "退货出库")
    public String genCode() throws Exception {
        StringBuffer code = new StringBuffer("TH");
        code.append(DateUtil.getCurrentDateStr());
        String returnNumber = returnListService.getTodayMaxReturnNumber();
        if (returnNumber != null) {
            code.append(StringUtil.formatCode(returnNumber));
        } else {
            code.append("0001");
        }
        return code.toString();
    }

    /**
     * 添加退货单 以及所有退货单商品
     *
     * @param returnList
     * @param goodsJson
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "退货出库")
    public Map<String, Object> save(ReturnList returnList, String goodsJson) {
        Map<String, Object> resultMap = new HashMap<>(16);
        //设置操作用户
        returnList.setUser(userService.findByUserName(SecurityUtils.getSubject().getPrincipal().toString()));
        Gson gson = new Gson();
        List<ReturnListGoods> returnListGoodsList = gson.fromJson(goodsJson, new TypeToken<List<ReturnListGoods>>() {
        }.getType());
        returnListService.save(returnList, returnListGoodsList);
        logService.save(new Log(Log.ADD_ACTION, "添加退货单"));
        resultMap.put("success", true);
        return resultMap;
    }
}
