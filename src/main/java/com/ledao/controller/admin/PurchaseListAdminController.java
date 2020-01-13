package com.ledao.controller.admin;

import com.ledao.service.LogService;
import com.ledao.service.PurchaseListGoodsService;
import com.ledao.service.PurchaseListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    private LogService logService;
}
