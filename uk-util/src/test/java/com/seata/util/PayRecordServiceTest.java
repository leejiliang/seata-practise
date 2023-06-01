package com.seata.util;

import com.seata.util.entity.PayRecord;
import com.seata.util.service.PayRecordService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @auther: jia.you
 * @date: 2023/05/30/10:33 AM
 * @version 1.0
 * @description:
 */

@SpringBootTest(classes = UkUtilApplication.class)
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

        PayRecord payRecord2 = new PayRecord();
        payRecord2.setAccountId(2L);
        payRecord2.setPayAmount(new BigDecimal(2));
        payRecord2.setPayTime(LocalDateTime.now());
        payRecordService.seateInsert(payRecord, payRecord2);
    }


}
