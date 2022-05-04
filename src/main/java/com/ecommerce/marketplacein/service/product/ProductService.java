package com.ecommerce.marketplacein.service.product;

import com.marketplace.marketplacecommon.product.dto.*;

public interface ProductService {

    void receiveProduct(ProductDTO productDTO);

    void updatePrice(ProductPriceUpdateDTO productPriceUpdateDTO);

    void updateStock(ProductStockUpdateDTO productStockUpdateDTO);

    void updateDeliveryData(ProductDeliveryDataUpdateDTO productDeliveryDataUpdateDTO);

    void updateStatus(ProductStatusUpdateDTO productStatusUpdateDTO);

}
