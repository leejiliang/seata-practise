package com.seata.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seata.order.entity.TOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther: jia.you
 * @date: 2023/05/30/10:15 AM
 * @version 1.0
 * @description:
 */
@Mapper
public interface OrderMapper extends BaseMapper<TOrder> {
}
