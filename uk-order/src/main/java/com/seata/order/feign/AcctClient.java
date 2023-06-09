package com.seata.order.feign;

import com.seata.order.feign.impl.AcctClientImpl;
import io.seata.core.context.RootContext;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @auther: jia.you
 * @date: 2023/05/26/4:43 PM
 * @version 1.0
 * @description:
 */

@FeignClient(name = "uk-acct", fallback = AcctClientImpl.class)
public interface AcctClient {
    @GetMapping(value = "/my-health-check")
    String index();

    @PutMapping(value = "/accounts")
    Long pay(@RequestParam(name = "userId") Long userId,
             @RequestParam(name = "amount") BigDecimal amount,
             @RequestHeader(RootContext.KEY_XID) String xid);
}
