package com.seata.util.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seata.util.entity.PayRecord;
import com.seata.util.mapper.PayRecordMapper;
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
    public void seateInsert(PayRecord payRecord1, PayRecord payRecord2){
        PayRecordService payRecordService = (PayRecordService) AopContext.currentProxy();
        payRecordService.insertMaster1(payRecord1);
        int i = 1/0;
        payRecordService.insertMaster1(payRecord2);
    }


    @DS("master") // 随机master
    @Transactional
    public void insertRandom(PayRecord payRecord){
        payRecordMapper.insert(payRecord);
    }

    @DS("master1")
    @Transactional
    public void insertMaster1(PayRecord payRecord){
        payRecordMapper.insert(payRecord);
    }

    @DS("master2")
    @Transactional
    public void insertMaster2(PayRecord payRecord){
        payRecordMapper.insert(payRecord);
    }



}
