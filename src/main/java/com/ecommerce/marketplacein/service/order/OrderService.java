package com.ecommerce.marketplacein.service.order;

import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentDto;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceOrderDto;

import java.io.IOException;

public interface OrderService {

    void receiveOrder(EcommerceConsignmentDto ecommerceConsignmentDto) throws IOException;

}
