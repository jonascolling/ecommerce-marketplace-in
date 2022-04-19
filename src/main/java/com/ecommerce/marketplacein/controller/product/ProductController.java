package com.ecommerce.marketplacein.controller.product;

import com.ecommerce.marketplacein.service.product.ProductService;
import com.marketplace.marketplacecommon.product.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void receiveProduct(@RequestBody ProductDTO productDTO) {
        productService.receiveProduct(productDTO);
    }

}
