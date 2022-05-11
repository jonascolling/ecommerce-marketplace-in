package com.ecommerce.marketplacein.replication;

import com.ecommerce.marketplacein.dto.CSEventDto;
import com.ecommerce.marketplacein.service.httpService.HttpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.marketplacecommon.dto.order.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CentralSellerOrderReplicationImpl implements CentralSellerOrderReplication {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${centralseller.api}")
    private String centralSellerApi;

    @Value("${centralseller.api.key}")
    private String centralSellerApiKey;

    @Autowired
    private HttpService httpService;

    @Override
    public void postCentralSellerOrder(OrderDto orderDto) throws IOException {
        sendObjectToCentralSeller(orderDto, "marketplace-order");
    }

    private ResponseEntity<Object> sendObjectToCentralSeller(Object object, String type) throws IOException {
        HttpHeaders headers = getHeaders();

        CSEventDto objectBodyToSend = getObjectBodyToSend(object, type);
        HttpEntity<Object> objectHttpEntity = new HttpEntity<>(objectBodyToSend, headers);

        return httpService.exchange(centralSellerApi, HttpMethod.POST, objectHttpEntity, getHeaders(), Object.class);
    }

    private CSEventDto getObjectBodyToSend(Object obj, String type) throws JsonProcessingException {
        String bodyFormatted = objectMapper.writeValueAsString(obj);

        return new CSEventDto(type, bodyFormatted);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-client-id", centralSellerApiKey);

        return headers;
    }

}
