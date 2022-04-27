package com.ecommerce.marketplacein.replication;

import com.marketplace.marketplacecommon.ecommerceproduct.dto.EcommerceProductDTO;

public interface EcommerceProductReplication {

    void postEcommerceProduct(EcommerceProductDTO ecommerceProductDTO);

}
