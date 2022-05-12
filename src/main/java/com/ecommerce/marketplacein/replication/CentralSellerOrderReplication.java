package com.ecommerce.marketplacein.replication;

import com.marketplace.marketplacecommon.dto.order.OrderDto;
import com.marketplace.marketplacecommon.dto.order.PaymentConfirmationDto;

import java.io.IOException;

public interface CentralSellerOrderReplication {

    void postCentralSellerOrder(OrderDto orderDto) throws IOException;

    void postCentralSellerOrderPaymentConfirmation(PaymentConfirmationDto paymentConfirmationDto) throws IOException;

}
