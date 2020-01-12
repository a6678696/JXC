package com.ledao.controller.admin;

import com.ledao.entity.Goods;
import com.ledao.entity.Log;
import com.ledao.service.GoodsService;
import com.ledao.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理商品Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-12 13:54
 */
@RestController
@RequestMapping("/admin/goods")
public class GoodsAdminController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private LogService logService;

    /**
     * 分页查询商品信息
     *
     * @param searchGoods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions(value = "商品管理")
    public Map<String, Object> list(Goods searchGoods, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<Goods> goodsList = goodsService.list(searchGoods, page, rows);
        Long total = goodsService.getCount(searchGoods);
        resultMap.put("rows", goodsList);
        resultMap.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息"));
        return resultMap;
    }
}
