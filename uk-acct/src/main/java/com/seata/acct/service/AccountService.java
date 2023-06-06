package com.seata.acct.service;

import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.seata.acct.client.WareHouseClient;
import com.seata.acct.entity.PayRecord;
import com.seata.acct.entity.TAccount;
import com.seata.acct.entity.repository.PayRecordRepository;
import com.seata.acct.entity.repository.TAccountRepository;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final TAccountRepository accountRepository;
    private final PayRecordRepository payRecordRepository;
    private final WareHouseClient wareHouseClient;

    @Transactional
    public Long createAccount(TAccount account) {
        return accountRepository.save(account).getId();
    }

    @Transactional
    public Long pay(Long userId, BigDecimal amount, String xid) {
//        RootContext.bind(xid);
        System.out.println("获取全局事务id：" + RootContext.getXID());
        System.out.println("acct是否在全局事务中："  + RootContext.inGlobalTransaction());
        TAccount account = new TAccount();
        account.setAmount(amount);
        account.setUserId(userId);
        return accountRepository.save(account).getId();
    }

    private Long payRecord(PayRecord payRecord) {
        return payRecordRepository.save(payRecord).getId();
    }
}
