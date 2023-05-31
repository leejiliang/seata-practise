package com.seata.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seata.warehouse.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description TODO
 */
@Mapper
public interface StockMapper extends BaseMapper<Stock> {
}
