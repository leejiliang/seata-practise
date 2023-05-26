package com.seata.acct.service;

import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.seata.acct.entity.PayRecord;
import com.seata.acct.entity.TAccount;
import com.seata.acct.entity.repository.PayRecordRepository;
import com.seata.acct.entity.repository.TAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final TAccountRepository accountRepository;
    private final PayRecordRepository payRecordRepository;

    @Transactional
    public Long createAccount(TAccount account) {
        return accountRepository.save(account).getId();
    }

    @Transactional
    public Long pay(Long userId, BigDecimal amount) {
        return accountRepository.findByUserId(userId).map(account -> {
            if (amount.compareTo(account.getAmount()) > 0) {
                throw new RuntimeException("余额不足");
            }
            account.setAmount(account.getAmount().subtract(amount));
            accountRepository.save(account);
            return payRecord(account.newRecord(amount));
        }).orElseThrow();
    }

    private Long payRecord(PayRecord payRecord) {
        return payRecordRepository.save(payRecord).getId();
    }
}
