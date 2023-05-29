package com.seata.order.service;

import com.seata.order.entity.TOrder;
import com.seata.order.entity.repository.OrderRepository;
import com.seata.order.feign.AcctClient;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AcctClient acctClient;

    @SneakyThrows
    @GlobalTransactional
    public Long createOrder(TOrder order) {
        var xid = RootContext.getXID();
        try {
            Long payRecordId = acctClient.pay(order.getUserId(), order.getAmount(), xid);
            order.setPayRecordId(payRecordId);
            TOrder tOrder = orderRepository.save(order);
            int i = 1 / 0;
            return tOrder.getId();
        } catch (Exception e) {
            GlobalTransactionContext.reload(xid).rollback();
            throw e;
        }
    }
}
