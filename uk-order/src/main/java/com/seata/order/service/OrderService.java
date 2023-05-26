package com.seata.order.service;

import com.seata.order.entity.TOrder;
import com.seata.order.entity.repository.OrderRepository;
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

    @Transactional
    public Long createOrder(TOrder order) {
        return orderRepository.save(order).getId();
    }
}
