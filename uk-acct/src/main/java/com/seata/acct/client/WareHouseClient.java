package com.seata.acct.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description TODO
 */
@FeignClient(value = "uk-warehouse", path = "/stocks")
public interface WareHouseClient {
    @PutMapping("/reduce")
    void deduct(@RequestParam(name = "commodityCode") String commodityCode,
                @RequestParam(name = "count") int count,
                @RequestParam(name = "xid") String xid);
}
