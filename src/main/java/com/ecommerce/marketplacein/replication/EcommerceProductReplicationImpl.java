package com.ecommerce.marketplacein.replication;

import com.ecommerce.marketplacein.service.httpService.HttpService;
import com.ecommerce.marketplacein.utils.OrderUtil;
import com.marketplace.marketplacecommon.dto.ecommerceproduct.*;
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
    public void postEcommerceProduct(EcommerceProductDto ecommerceProductDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products", HttpMethod.POST, ecommerceProductDTO, OrderUtil.getEcommerceHeaderReques(), Object.class);
    }

    @Override
    public void putEcommerceProductPrice(EcommerceProductPriceUpdateDto ecommerceProductPriceUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/price", HttpMethod.PUT, ecommerceProductPriceUpdateDTO, OrderUtil.getEcommerceHeaderReques(), Object.class);
    }

    @Override
    public void putEcommerceProductStock(EcommerceProductStockUpdateDto ecommerceProductStockUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/stock", HttpMethod.PUT, ecommerceProductStockUpdateDTO, OrderUtil.getEcommerceHeaderReques(), Object.class);
    }

    @Override
    public void putEcommerceProductDeliveryData(EcommerceProductDeliveryDataUpdateDto ecommerceProductDeliveryDataUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/delivery-data", HttpMethod.PUT, ecommerceProductDeliveryDataUpdateDTO, OrderUtil.getEcommerceHeaderReques(), Object.class);
    }

    @Override
    public void putEcommerceProductStatus(EcommerceProductStatusUpdateDto ecommerceProductStatusUpdateDTO) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-products/status", HttpMethod.PUT, ecommerceProductStatusUpdateDTO, OrderUtil.getEcommerceHeaderReques(), Object.class);
    }

}
