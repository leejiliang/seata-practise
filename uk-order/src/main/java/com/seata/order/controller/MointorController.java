package com.seata.order.controller;

import com.seata.order.feign.AcctClient;
import com.seata.order.feign.WarehouseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther: jia.you
 * @date: 2023/05/26/4:56 PM
 * @version 1.0
 * @description:
 */

@RestController
@RequestMapping("/monitor")
public class MointorController {

    @Autowired
    private AcctClient acctClient;

    @Autowired
    private WarehouseClient warehouseClient;

    @GetMapping("/acct")
    public String acctHealth(){
        return acctClient.index();
    }

    @GetMapping("/warehouse")
    public String warehouseHealth(){
        return warehouseClient.index();
    }
}
