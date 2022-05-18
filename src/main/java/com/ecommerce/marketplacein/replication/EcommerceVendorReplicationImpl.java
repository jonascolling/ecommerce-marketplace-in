package com.ecommerce.marketplacein.replication;

import com.ecommerce.marketplacein.service.httpService.HttpService;
import com.ecommerce.marketplacein.utils.OrderUtil;
import com.marketplace.marketplacecommon.dto.ecommercevendor.EcommerceVendorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class EcommerceVendorReplicationImpl implements EcommerceVendorReplication {

    @Autowired
    private HttpService httpService;

    @Value("${ecommerce.api}")
    private String ecommerceApiUrl;

    @Override
    public void postEcommerceVendor(EcommerceVendorDto ecommerceVendorDto) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-vendor", HttpMethod.POST, ecommerceVendorDto, OrderUtil.getEcommerceHeaderReques(), Object.class);
    }

}
