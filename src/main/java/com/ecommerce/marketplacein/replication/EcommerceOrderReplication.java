package com.ecommerce.marketplacein.replication;

import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentComissionDto;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentStatusUpdateDto;

public interface EcommerceOrderReplication {

    void postEcommerceOrderStatus(EcommerceConsignmentStatusUpdateDto consignmentStatusUpdateDto);

    void postEcommerceOrderComission(EcommerceConsignmentComissionDto consignmentComissionDto);

}
