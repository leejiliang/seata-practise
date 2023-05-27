package com.seata.order.feign.impl;

import com.seata.order.feign.AcctClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @auther: jia.you
 * @date: 2023/05/26/4:49 PM
 * @version 1.0
 * @description:
 */

public class AcctClientImpl implements AcctClient {
    @Override
    public String index() {
        return "acct health check error";
    }

    @Override
    public Long pay(Long userId, BigDecimal amount) {
        throw new RuntimeException("支付异常");
    }
}
