package com.ecommerce.marketplacein.replication;

import com.marketplace.marketplacecommon.dto.ecommerceproduct.*;

public interface EcommerceProductReplication {

    void postEcommerceProduct(EcommerceProductDto ecommerceProductDTO);

    void putEcommerceProductPrice(EcommerceProductPriceUpdateDto ecommerceProductPriceUpdateDTO);

    void putEcommerceProductStock(EcommerceProductStockUpdateDto ecommerceProductStockUpdateDTO);

    void putEcommerceProductDeliveryData(EcommerceProductDeliveryDataUpdateDto ecommerceProductDeliveryDataUpdateDTO);

    void putEcommerceProductStatus(EcommerceProductStatusUpdateDto ecommerceProductStatusUpdateDTO);

}
