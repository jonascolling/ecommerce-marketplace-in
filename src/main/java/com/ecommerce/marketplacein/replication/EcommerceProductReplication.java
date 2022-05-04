package com.ecommerce.marketplacein.replication;

import com.marketplace.marketplacecommon.ecommerceproduct.dto.*;
import com.marketplace.marketplacecommon.product.dto.ProductDeliveryDataUpdateDTO;
import com.marketplace.marketplacecommon.product.dto.ProductPriceUpdateDTO;
import com.marketplace.marketplacecommon.product.dto.ProductStatusUpdateDTO;
import com.marketplace.marketplacecommon.product.dto.ProductStockUpdateDTO;

public interface EcommerceProductReplication {

    void postEcommerceProduct(EcommerceProductDTO ecommerceProductDTO);

    void putEcommerceProductPrice(EcommerceProductPriceUpdateDTO ecommerceProductPriceUpdateDTO);

    void putEcommerceProductStock(EcommerceProductStockUpdateDTO ecommerceProductStockUpdateDTO);

    void putEcommerceProductDeliveryData(EcommerceProductDeliveryDataUpdateDTO ecommerceProductDeliveryDataUpdateDTO);

    void putEcommerceProductStatus(EcommerceProductStatusUpdateDTO ecommerceProductStatusUpdateDTO);

}
