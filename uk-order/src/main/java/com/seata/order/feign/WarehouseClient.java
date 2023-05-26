package com.seata.order.feign;

import com.seata.order.feign.impl.WarehouseClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auther: jia.you
 * @date: 2023/05/26/4:43 PM
 * @version 1.0
 * @description:
 */

@FeignClient(name = "uk-warehouse", fallback = WarehouseClientImpl.class)
public interface WarehouseClient {

    @GetMapping(value = "/my-health-check")
    String index();
}
