package com.ecommerce.marketplacein.service.order;

import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentDto;

import java.io.IOException;

public interface ReceiveOrderPaymentConfirmationService {

    void receiveOrderPaymentConfirmation(EcommerceConsignmentDto ecommerceConsignmentDto) throws IOException;

}
