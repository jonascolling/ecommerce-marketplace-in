package com.ecommerce.marketplacein.listener;

import com.ecommerce.marketplacein.service.order.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentDto;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderQueueListener {

    @Autowired
    private OrderService orderService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @JmsListener(destination="ecommerce_marketplacein_new-order")
    public void receiveNewOrderFromEcommerce(String queueData) throws IOException {
        EcommerceConsignmentDto ecommerceConsignmentDto = objectMapper.readValue(queueData, EcommerceConsignmentDto.class);
        orderService.receiveOrder(ecommerceConsignmentDto);
    }

}
