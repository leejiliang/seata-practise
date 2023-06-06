package com.seata.acct.controller;

import com.seata.acct.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PutMapping
    public Long pay(@RequestParam Long userId,
                    @RequestParam BigDecimal amount,
                    @RequestParam(name = "xid", required = false) String xid) {
        return accountService.pay(userId, amount, xid);
    }
}
