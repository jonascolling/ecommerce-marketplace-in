package com.ecommerce.marketplacein.replication;

import com.marketplace.marketplacecommon.dto.order.OrderDto;

import java.io.IOException;

public interface CentralSellerOrderReplication {

    void postCentralSellerOrder(OrderDto orderDto) throws IOException;

}
