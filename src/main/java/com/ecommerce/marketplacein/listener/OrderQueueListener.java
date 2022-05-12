package com.ecommerce.marketplacein.listener;

import com.ecommerce.marketplacein.service.order.ReceiveOrderPaymentConfirmationService;
import com.ecommerce.marketplacein.service.order.ReceiveOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderQueueListener {

    @Autowired
    private ReceiveOrderService receiveOrderService;

    @Autowired
    private ReceiveOrderPaymentConfirmationService receiveOrderPaymentConfirmationService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @JmsListener(destination="ecommerce_marketplacein_new-order")
    public void receiveNewOrderFromEcommerce(String queueData) throws IOException {
        EcommerceConsignmentDto ecommerceConsignmentDto = objectMapper.readValue(queueData, EcommerceConsignmentDto.class);
        receiveOrderService.receiveOrder(ecommerceConsignmentDto);
    }

    @JmsListener(destination="ecommerce_marketplacein_order-payment-confirmation")
    public void receiveOrderPaymentConfirmationFromEcommerce(String queueData) throws IOException {
        EcommerceConsignmentDto ecommerceConsignmentDto = objectMapper.readValue(queueData, EcommerceConsignmentDto.class);
        receiveOrderPaymentConfirmationService.receiveOrderPaymentConfirmation(ecommerceConsignmentDto);
    }

}
