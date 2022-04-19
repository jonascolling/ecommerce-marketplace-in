package com.ecommerce.marketplacein.service.product;

import com.ecommerce.marketplacein.exception.InvalidProductException;
import com.marketplace.marketplacecommon.product.dto.ProductDTO;

public interface ProductService {

    void receiveProduct(ProductDTO productDTO) throws InvalidProductException;

}
