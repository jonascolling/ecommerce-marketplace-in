package com.ecommerce.marketplacein.controller.product;

import com.marketplace.marketplacecommon.product.dto.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void receiveProduct(@RequestBody ProductDTO productDTO) {
        System.out.println("dsd");
    }

}
