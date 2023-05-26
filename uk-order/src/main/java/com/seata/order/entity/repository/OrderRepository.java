package com.seata.order.entity.repository;

import com.seata.order.entity.TOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 */
@Repository
public interface OrderRepository extends JpaRepository<TOrder, Long> {
}
