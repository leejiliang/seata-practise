package com.seata.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.springframework.context.annotation.Configuration;

/**
 * @auther: jia.you
 * @date: 2023/06/06/3:44 PM
 * @version 1.0
 * @description:
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        //
        String xid = RootContext.getXID();
//        template.header(RootContext.KEY_XID, RootContext.getXID());
    }

}
