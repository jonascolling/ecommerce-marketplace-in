package com.ecommerce.marketplacein.replication;

import com.ecommerce.marketplacein.service.httpService.HttpService;
import com.marketplace.marketplacecommon.ecommerceproduct.dto.EcommerceProductDTO;
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

}
