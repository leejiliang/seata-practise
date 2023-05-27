package com.seata.acct.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "t_account")
public class TAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private BigDecimal amount;

    public PayRecord newRecord(BigDecimal payAmount) {
        var record = new PayRecord();
        record.setAccountId(id);
        record.setPayAmount(payAmount);
        record.setPayTime(LocalDateTime.now());
        return record;
    }
}
