package com.seata.order.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description TODO
 */
@Data
public class TOrder {

    private Long id;
    private String orderNo;
    private Long userId;
    private String commodityCode;
    private Integer count;
    private BigDecimal amount;

    private Long payRecordId;
}
