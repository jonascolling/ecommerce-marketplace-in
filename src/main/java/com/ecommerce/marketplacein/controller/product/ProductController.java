package com.ecommerce.marketplacein.controller.product;

import com.ecommerce.marketplacein.service.product.ProductService;
import com.marketplace.marketplacecommon.product.dto.*;
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

    @PutMapping(value = "/price")
    @ResponseStatus(HttpStatus.OK)
    public void updatePrice(@RequestBody ProductPriceUpdateDTO productPriceUpdateDTO) {
        productService.updatePrice(productPriceUpdateDTO);
    }

    @PutMapping(value = "/stock")
    @ResponseStatus(HttpStatus.OK)
    public void updateStock(@RequestBody ProductStockUpdateDTO productStockUpdateDTO) {
        productService.updateStock(productStockUpdateDTO);
    }

    @PutMapping(value = "/delivery-data")
    @ResponseStatus(HttpStatus.OK)
    public void updateDeliveryData(@RequestBody ProductDeliveryDataUpdateDTO productDeliveryDataUpdateDTO) {
        productService.updateDeliveryData(productDeliveryDataUpdateDTO);
    }

    @PutMapping(value = "/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(@RequestBody ProductStatusUpdateDTO productStatusUpdateDTO) {
        productService.updateStatus(productStatusUpdateDTO);
    }

}
