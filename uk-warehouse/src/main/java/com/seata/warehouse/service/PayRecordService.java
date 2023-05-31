package com.seata.warehouse.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seata.warehouse.entity.PayRecord;
import com.seata.warehouse.mapper.PayRecordMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

/**
 * @auther: jia.you
 * @date: 2023/05/31/10:59 AM
 * @version 1.0
 * @description:
 */

@Service
@Slf4j
public class PayRecordService {

    @Autowired
    private PayRecordMapper payRecordMapper;


    /**
     * 这里演示的是未使用seata时，事务不生效。不能加@Transactional注解，会导致数据源无法切换
     */
    @Transactional
    @SneakyThrows
    public void testTransaction(PayRecord payRecord1, PayRecord payRecord2){
        PayRecordService payRecordService = (PayRecordService) AopContext.currentProxy();
//        payRecordService.insertMaster1(payRecord1);
//        payRecordService.insertMaster2(payRecord2);
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> log.info("查询s1结果：" + payRecordService.query1().getAccountId()));
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> log.info("查询s2结果：" + payRecordService.query2().getAccountId()));

        CompletableFuture.allOf(future1, future2).join();
    }

    @DS("master") // 随机master
    @Transactional
    public void insertRandom(PayRecord payRecord){
        payRecordMapper.insert(payRecord);
    }

    @DS("master_1")
    @Transactional
    public void insertMaster1(PayRecord payRecord){
        payRecordMapper.insert(payRecord);
    }

    @DS("master_2")
    @Transactional
    public void insertMaster2(PayRecord payRecord){
        payRecordMapper.insert(payRecord);
    }

    @DS("slave")
    public PayRecord queryRandom(){
        LambdaQueryWrapper<PayRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PayRecord :: getId, 1);
        return payRecordMapper.selectOne(wrapper);
    }

    @DS("slave_1")
    public PayRecord query1(){
        LambdaQueryWrapper<PayRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PayRecord :: getId, 1);
        return payRecordMapper.selectOne(wrapper);
    }

    @DS("slave_2")
    public PayRecord query2(){
        LambdaQueryWrapper<PayRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PayRecord :: getId, 1);
        return payRecordMapper.selectOne(wrapper);
    }


}
