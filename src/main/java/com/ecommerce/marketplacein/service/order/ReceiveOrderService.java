package com.ecommerce.marketplacein.service.order;

import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentDto;

import java.io.IOException;

public interface ReceiveOrderService {

    void receiveOrder(EcommerceConsignmentDto ecommerceConsignmentDto) throws IOException;

}
