package com.seata.order.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @Description TODO
 */
@Entity
@Data
@Table(name = "t_order")
public class TOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;
    private Long userId;
    private String commodityCode;
    private Integer count;
    private BigDecimal amount;

    private Long payRecordId;
}
