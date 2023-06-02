package com.seata.order.service;

import com.seata.order.entity.TOrder;
import com.seata.order.feign.AcctClient;
import com.seata.order.mapper.OrderMapper;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description TODO
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final AcctClient acctClient;
    private final OrderMapper orderMapper;

    @SneakyThrows
    @GlobalTransactional
    public Long createOrder(TOrder order) {
        var xid = RootContext.getXID();
        try {
            Long payRecordId = acctClient.pay(order.getUserId(), order.getAmount(), "111");
            order.setPayRecordId(payRecordId);
//            TOrder tOrder = orderRepository.save(order);
//            orderMapper.insert(order);
//            var orderService = (OrderService) AopContext.currentProxy();
//            orderService.saveOrder(order);
            int i = 1 / 0;
            return 0l;
        } catch (Exception e) {
//            GlobalTransactionContext.reload(xid).rollback();
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long saveOrder(TOrder order) {
        orderMapper.insert(order);
        return order.getId();
    }
}
