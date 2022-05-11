package com.ecommerce.marketplacein.service.product;

import com.marketplace.marketplacecommon.dto.product.*;

public interface ProductService {

    void receiveProduct(ProductDto productDTO);

    void updatePrice(ProductPriceUpdateDto productPriceUpdateDTO);

    void updateStock(ProductStockUpdateDto productStockUpdateDTO);

    void updateDeliveryData(ProductDeliveryDataUpdateDto productDeliveryDataUpdateDTO);

    void updateStatus(ProductStatusUpdateDto productStatusUpdateDTO);

}
