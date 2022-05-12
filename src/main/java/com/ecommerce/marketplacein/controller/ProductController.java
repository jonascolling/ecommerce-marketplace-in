package com.ecommerce.marketplacein.controller;

import com.ecommerce.marketplacein.service.product.ProductService;
import com.marketplace.marketplacecommon.dto.product.*;
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
    public void receiveProduct(@RequestBody ProductDto productDTO) {
        productService.receiveProduct(productDTO);
    }

    @PutMapping(value = "/price")
    @ResponseStatus(HttpStatus.OK)
    public void updatePrice(@RequestBody ProductPriceUpdateDto productPriceUpdateDTO) {
        productService.updatePrice(productPriceUpdateDTO);
    }

    @PutMapping(value = "/stock")
    @ResponseStatus(HttpStatus.OK)
    public void updateStock(@RequestBody ProductStockUpdateDto productStockUpdateDTO) {
        productService.updateStock(productStockUpdateDTO);
    }

    @PutMapping(value = "/delivery-data")
    @ResponseStatus(HttpStatus.OK)
    public void updateDeliveryData(@RequestBody ProductDeliveryDataUpdateDto productDeliveryDataUpdateDTO) {
        productService.updateDeliveryData(productDeliveryDataUpdateDTO);
    }

    @PutMapping(value = "/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(@RequestBody ProductStatusUpdateDto productStatusUpdateDTO) {
        productService.updateStatus(productStatusUpdateDTO);
    }

}
