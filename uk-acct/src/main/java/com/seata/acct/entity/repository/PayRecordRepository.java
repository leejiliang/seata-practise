package com.seata.acct.entity.repository;

import com.seata.acct.entity.PayRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRecordRepository extends JpaRepository<PayRecord, Long> {
}
