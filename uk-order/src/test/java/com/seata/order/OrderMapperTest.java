package com.seata.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seata.order.entity.TOrder;
import com.seata.order.mappers.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @auther: jia.you
 * @date: 2023/05/30/10:33 AM
 * @version 1.0
 * @description:
 */

@SpringBootTest(classes = UkOrderApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testInsert(){
        TOrder order = new TOrder();
        order.setOrderNo(UUID.randomUUID().toString());
        order.setUserId(System.currentTimeMillis());
        order.setCommodityCode(UUID.randomUUID().toString());
        order.setCount(1);
        order.setAmount(BigDecimal.ZERO);
        order.setPayRecordId(System.currentTimeMillis());

        orderMapper.insert(order);
    }

    @Test
    public void testSelect(){
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TOrder:: getId, 1);
        TOrder order = orderMapper.selectOne(queryWrapper);
        System.out.println(order);
    }

    @Test
    public void testPage(){
        LambdaQueryWrapper<TOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TOrder:: getCount, 1);
        Page<TOrder> tOrderPage = orderMapper.selectPage(Page.of(1, 2), queryWrapper);
        if(tOrderPage != null && tOrderPage.getSize() > 0){
            log.info("查询总记录数：{}", tOrderPage.getTotal());
            tOrderPage.getRecords().forEach(t -> System.out.println(t));
        }
    }
}
