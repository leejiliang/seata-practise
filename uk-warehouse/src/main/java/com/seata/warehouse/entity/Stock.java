package com.seata.warehouse.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description TODO
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Stock extends Model<Stock> implements Serializable {
    private Long id;
    private String commodityCode;
    private Integer count;
    private LocalDateTime lastUpdateTime;
}
