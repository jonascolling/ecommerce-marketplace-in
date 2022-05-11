package com.ecommerce.marketplacein.replication;

import com.ecommerce.marketplacein.service.httpService.HttpService;
import com.marketplace.marketplacecommon.dto.ecommerceproduct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class EcommerceProductReplicationImpl implements EcommerceProductReplication {

    @Autowired
    private HttpService httpService;

    @Value("${ecommerce.api}")
    private String ecommerceApiUrl;

    @Override
    public void postEcommerceProduct(EcommerceProductDto ecommerceProductDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products", HttpMethod.POST, ecommerceProductDTO, getHeader(), Object.class);
    }

    @Override
    public void putEcommerceProductPrice(EcommerceProductPriceUpdateDto ecommerceProductPriceUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/price", HttpMethod.PUT, ecommerceProductPriceUpdateDTO, getHeader(), Object.class);
    }

    @Override
    public void putEcommerceProductStock(EcommerceProductStockUpdateDto ecommerceProductStockUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/stock", HttpMethod.PUT, ecommerceProductStockUpdateDTO, getHeader(), Object.class);
    }

    @Override
    public void putEcommerceProductDeliveryData(EcommerceProductDeliveryDataUpdateDto ecommerceProductDeliveryDataUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/delivery-data", HttpMethod.PUT, ecommerceProductDeliveryDataUpdateDTO, getHeader(), Object.class);
    }

    @Override
    public void putEcommerceProductStatus(EcommerceProductStatusUpdateDto ecommerceProductStatusUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/status", HttpMethod.PUT, ecommerceProductStatusUpdateDTO, getHeader(), Object.class);
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
