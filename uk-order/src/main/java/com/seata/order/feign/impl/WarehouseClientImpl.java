package com.seata.order.feign.impl;

import com.seata.order.feign.WarehouseClient;
import org.springframework.stereotype.Component;

/**
 * @auther: jia.you
 * @date: 2023/05/26/4:46 PM
 * @version 1.0
 * @description:
 */
@Component
public class WarehouseClientImpl implements WarehouseClient {
    @Override
    public String index() {
        return "warehouse health check error";
    }
}
