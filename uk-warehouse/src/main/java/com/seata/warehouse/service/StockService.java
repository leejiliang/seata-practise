package com.seata.warehouse.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seata.warehouse.entity.Stock;
import com.seata.warehouse.mapper.StockMapper;
import io.seata.core.context.RootContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Description TODO
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
    private final StockMapper stockMapper;

    @Transactional
    @DS("master")
    public void reduceStock(String commodityCode, int count, String xid) {
        RootContext.bind(xid);
        var stock = queryStock(commodityCode);
        stock.setCount(stock.getCount() - count);
        stock.updateById();
        log.info("扣减库存成功");
    }

    public Stock queryStock(String commodityCode) {
        var queryWrapper = new QueryWrapper<Stock>().lambda()
                .eq(Stock::getCommodityCode, commodityCode);
        return Optional.ofNullable(stockMapper.selectOne(queryWrapper)).orElseThrow();
    }
}
