package com.seata.warehouse.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seata.warehouse.entity.PayRecord;
import com.seata.warehouse.mapper.PayRecordMapper;
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
public class PayRecordService {

    @Autowired
    private PayRecordMapper payRecordMapper;


    /**
     * 这里演示的是未使用seata时，事务不生效。不能加@Transactional注解，会导致数据源无法切换
     */
    public void testTransaction(PayRecord payRecord1, PayRecord payRecord2){
        PayRecordService payRecordService = (PayRecordService) AopContext.currentProxy();
        var f1 = CompletableFuture.runAsync(() -> payRecordService.insertMaster1(payRecord1));
        var f2 = CompletableFuture.runAsync(() -> payRecordService.insertMaster2(payRecord2));
        CompletableFuture.allOf(f1, f2).whenComplete((r, e) -> {
            if(e != null){
                throw new RuntimeException(e);
            }
        }).join();
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
