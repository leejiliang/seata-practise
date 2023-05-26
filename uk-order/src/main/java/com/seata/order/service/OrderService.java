package com.seata.order.service;

import com.seata.order.entity.TOrder;
import com.seata.order.entity.repository.OrderRepository;
import com.seata.order.feign.AcctClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description TODO
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AcctClient acctClient;

    @Transactional
    public Long createOrder(TOrder order) {
        Long payRecordId = acctClient.pay(order.getUserId(), order.getAmount());
        order.setPayRecordId(payRecordId);
        TOrder tOrder = orderRepository.save(order);
        int i = 1 / 0;
        return tOrder.getId();
    }
}
