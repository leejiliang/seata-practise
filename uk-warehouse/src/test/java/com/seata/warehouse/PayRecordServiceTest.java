package com.seata.warehouse;

import com.seata.warehouse.entity.PayRecord;
import com.seata.warehouse.service.PayRecordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @auther: jia.you
 * @date: 2023/05/30/10:33 AM
 * @version 1.0
 * @description:
 */

@SpringBootTest(classes = UkWarehouseApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class PayRecordServiceTest {

    @Autowired
    private PayRecordService payRecordService;

    @Test
    public void testInsert(){
        PayRecord payRecord = new PayRecord();
        payRecord.setAccountId(1L);
        payRecord.setPayAmount(new BigDecimal(1));
        payRecord.setPayTime(LocalDateTime.now());
        payRecordService.insertRandom(payRecord);
        payRecordService.insertRandom(payRecord);
    }

    @Test
    public void testQuery(){
        for(int i = 1; i <= 10; i++){
            PayRecord payRecord = payRecordService.queryRandom();
            log.info("第{}查询结果：{}", i, payRecord.getAccountId());
        }
    }

    /**
     * 这里演示的是未使用seata时，事务不生效
     */
    @Test
    public void testTransaction(){
        PayRecord payRecord1 = new PayRecord();
        payRecord1.setAccountId(Long.valueOf(2));
        payRecord1.setPayAmount(new BigDecimal(2));
        payRecord1.setPayTime(LocalDateTime.now());

        PayRecord payRecord2 = new PayRecord();
        payRecord2.setAccountId(Long.valueOf(3));
        payRecord2.setPayAmount(new BigDecimal(3));
        payRecord2.setPayTime(LocalDateTime.now());
        payRecordService.testTransaction(payRecord1, payRecord2);
    }

}
