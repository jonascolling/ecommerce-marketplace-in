package com.ecommerce.marketplacein.replication;

import com.ecommerce.marketplacein.service.httpService.HttpService;
import com.ecommerce.marketplacein.utils.OrderUtil;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentComissionDto;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentStatusUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class EcommerceOrderReplicationImpl implements EcommerceOrderReplication {

    @Autowired
    private HttpService httpService;

    @Value("${ecommerce.api}")
    private String ecommerceApiUrl;

    @Override
    public void postEcommerceOrderStatus(EcommerceConsignmentStatusUpdateDto consignmentStatusUpdateDto) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-order-update/status", HttpMethod.POST, consignmentStatusUpdateDto, OrderUtil.getEcommerceHeaderReques(), Object.class);
    }

    @Override
    public void postEcommerceOrderComission(EcommerceConsignmentComissionDto consignmentComissionDto) {
        httpService.exchange(ecommerceApiUrl + "/marketplacezz/receive-order-update/comission", HttpMethod.POST, consignmentComissionDto, OrderUtil.getEcommerceHeaderReques(), Object.class);
    }

}
