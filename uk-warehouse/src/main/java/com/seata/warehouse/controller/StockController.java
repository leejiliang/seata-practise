package com.seata.warehouse.controller;

import com.seata.warehouse.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 */
@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @PutMapping("/reduce")
    public void reduceStock(@RequestParam(name = "commodityCode") String commodityCode,
                            @RequestParam(name = "count") int count,
                            @RequestParam(name = "xid") String xid) {
        stockService.reduceStock(commodityCode, count, xid);
    }
}
