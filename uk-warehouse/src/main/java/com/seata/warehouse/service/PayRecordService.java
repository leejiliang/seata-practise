package com.seata.warehouse.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seata.warehouse.entity.PayRecord;
import com.seata.warehouse.mapper.PayRecordMapper;
import io.seata.spring.annotation.GlobalTransactional;
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


    @GlobalTransactional
    public void insertSeata(PayRecord payRecord1, PayRecord payRecord2, int num){
        PayRecordService payRecordService = (PayRecordService) AopContext.currentProxy();
        payRecordService.insertMaster1(payRecord1);
        payRecordService.insertMaster2(payRecord2);
        int i = 1 / num;
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
