package com.ecommerce.marketplacein.replication;

import com.ecommerce.marketplacein.service.httpService.HttpService;
import com.marketplace.marketplacecommon.ecommerceproduct.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class EcommerceProductReplicationImpl implements EcommerceProductReplication {

    @Autowired
    private HttpService httpService;

    @Value("${ecommerce.api}")
    private String ecommerceApiUrl;

    @Override
    public void postEcommerceProduct(EcommerceProductDTO ecommerceProductDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products", HttpMethod.POST, ecommerceProductDTO, Object.class);
    }

    @Override
    public void putEcommerceProductPrice(EcommerceProductPriceUpdateDTO ecommerceProductPriceUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/price", HttpMethod.PUT, ecommerceProductPriceUpdateDTO, Object.class);
    }

    @Override
    public void putEcommerceProductStock(EcommerceProductStockUpdateDTO ecommerceProductStockUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/stock", HttpMethod.PUT, ecommerceProductStockUpdateDTO, Object.class);
    }

    @Override
    public void putEcommerceProductDeliveryData(EcommerceProductDeliveryDataUpdateDTO ecommerceProductDeliveryDataUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/delivery-data", HttpMethod.PUT, ecommerceProductDeliveryDataUpdateDTO, Object.class);
    }

    @Override
    public void putEcommerceProductStatus(EcommerceProductStatusUpdateDTO ecommerceProductStatusUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/status", HttpMethod.PUT, ecommerceProductStatusUpdateDTO, Object.class);
    }

}
