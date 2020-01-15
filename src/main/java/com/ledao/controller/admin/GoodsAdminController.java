package com.ledao.controller.admin;

import com.ledao.entity.Goods;
import com.ledao.entity.Log;
import com.ledao.service.CustomerReturnListGoodsService;
import com.ledao.service.GoodsService;
import com.ledao.service.LogService;
import com.ledao.service.SaleListGoodsService;
import com.ledao.util.StringUtil;
import org.apache.shiro.authz.annotation.Logical;
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
    private SaleListGoodsService saleListGoodsService;

    @Resource
    private CustomerReturnListGoodsService customerReturnListGoodsService;

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
    @RequiresPermissions(value = {"商品管理", "进货入库", "退货出库"}, logical = Logical.OR)
    public Map<String, Object> list(Goods searchGoods, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<Goods> goodsList = goodsService.list(searchGoods, page, rows);
        Long total = goodsService.getCount(searchGoods);
        resultMap.put("rows", goodsList);
        resultMap.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息"));
        return resultMap;
    }

    /**
     * 查询库存报警商品
     *
     * @return
     */
    @RequestMapping("/listAlarm")
    @RequiresPermissions(value = "库存报警")
    public Map<String, Object> listAlarm() {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("rows", goodsService.listAlarm());
        logService.save(new Log(Log.SEARCH_ACTION, "查询库存报警商品信息"));
        return resultMap;
    }

    /**
     * 根据条件分页查询商品库存信息
     *
     * @param searchGoods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/listInventory")
    @RequiresPermissions(value = "当前库存查询")
    public Map<String, Object> listInventory(Goods searchGoods, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<Goods> goodsList = goodsService.list(searchGoods, page, rows);
        for (Goods goods : goodsList) {
            //设置销售总量
            goods.setSaleTotal(saleListGoodsService.getTotalByGoodsId(goods.getId()) - customerReturnListGoodsService.getTotalByGoodsId(goods.getId()));
        }
        Long total = goodsService.getCount(searchGoods);
        resultMap.put("rows", goodsList);
        resultMap.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息"));
        return resultMap;
    }

    /**
     * 根据条件分页查询没有库存的商品信息
     *
     * @param codeOrName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/listNoInventoryQuantity")
    @RequiresPermissions(value = "期初库存")
    public Map<String, Object> listNoInventoryQuantity(@RequestParam(value = "codeOrName", required = false) String codeOrName, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<Goods> goodsList = goodsService.listNoInventoryQuantityByCodeOrName(codeOrName, page, rows);
        Long total = goodsService.getCountNoInventoryQuantityByCodeOrName(codeOrName);
        resultMap.put("rows", goodsList);
        resultMap.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息(无库存)"));
        return resultMap;
    }

    /**
     * 根据条件分页查询有库存的商品信息
     *
     * @param codeOrName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/listHasInventoryQuantity")
    @RequiresPermissions(value = "期初库存")
    public Map<String, Object> listHasInventoryQuantity(@RequestParam(value = "codeOrName", required = false) String codeOrName, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<Goods> goodsList = goodsService.listHasInventoryQuantityByCodeOrName(codeOrName, page, rows);
        Long total = goodsService.getCountHasInventoryQuantityByCodeOrName(codeOrName);
        resultMap.put("rows", goodsList);
        resultMap.put("total", total);
        logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息(有库存)"));
        return resultMap;
    }

    /**
     * 生成商品编码
     *
     * @return
     */
    @RequestMapping("/genGoodsCode")
    @RequiresPermissions(value = "商品管理")
    public String genGoodsCode() {
        String maxGoodsCode = goodsService.getMaxGoodsCode();
        if (StringUtil.isNotEmpty(maxGoodsCode)) {
            Integer code = Integer.parseInt(maxGoodsCode) + 1;
            StringBuilder codes = new StringBuilder(code + "");
            int length = codes.length();
            for (int i = 4; i > length; i--) {
                codes.insert(0, "0");
            }
            return codes.toString();
        } else {
            return "0001";
        }
    }

    /**
     * 添加或者修改商品信息
     *
     * @param goods
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions(value = "商品管理")
    public Map<String, Object> save(Goods goods) {
        Map<String, Object> resultMap = new HashMap<>(16);
        if (goods.getId() != null) {
            logService.save(new Log(Log.UPDATE_ACTION, "更新商品信息" + goods));
        } else {
            logService.save(new Log(Log.ADD_ACTION, "添加商品信息" + goods));
            // 设置上次进价为当前价格
            goods.setLastPurchasingPrice(goods.getPurchasingPrice());
            goods.setInventoryQuantity(0);
        }
        goodsService.save(goods);
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 删除商品信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions(value = "商品管理")
    public Map<String, Object> delete(Integer id) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Goods goods = goodsService.findById(id);
        if (goods.getState() == 1) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "该商品已经期初入库，不能删除");
        } else if (goods.getState() == 2) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "该商品已经发生单据，不能删除");
        } else {
            logService.save(new Log(Log.DELETE_ACTION, "删除商品信息" + goods));
            goodsService.delete(id);
            resultMap.put("success", true);
        }
        return resultMap;
    }

    /**
     * 添加商品到仓库 修改库存以及价格信息
     *
     * @param id
     * @param num
     * @param price
     * @return
     */
    @RequestMapping("/saveStore")
    @RequiresPermissions(value = "期初库存")
    public Map<String, Object> saveStore(Integer id, Integer num, Float price) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Goods goods = goodsService.findById(id);
        goods.setInventoryQuantity(num);
        goods.setPurchasingPrice(price);
        goods.setLastPurchasingPrice(price);
        goodsService.save(goods);
        logService.save(new Log(Log.UPDATE_ACTION, "修改商品信息" + goods + "，价格=" + price + ",库存=" + num));
        resultMap.put("success", true);
        return resultMap;
    }

    @RequestMapping("/deleteStock")
    @RequiresPermissions(value = "期初库存")
    public Map<String, Object> deleteStock(Integer id) {
        Map<String, Object> resultMap = new HashMap<>(16);
        Goods goods = goodsService.findById(id);
        //商品状态:2代表有进货或者销售单据
        int goodsState = 2;
        if (goods.getState() == goodsState) {
            resultMap.put("success", false);
            resultMap.put("errorInfo", "该商品已经发生单据，不能删除!");
        } else {
            goods.setInventoryQuantity(0);
            goodsService.save(goods);
            logService.save(new Log(Log.UPDATE_ACTION, "修改商品信息" + goods));
            resultMap.put("success", true);
        }
        return resultMap;
    }
}
