package com.seata.acct.entity.repository;

import com.seata.acct.entity.TAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TAccountRepository extends JpaRepository<TAccount, Long> {

    Optional<TAccount> findByUserId(Long userId);
}
