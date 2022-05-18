package com.ecommerce.marketplacein.controller;

import com.ecommerce.marketplacein.service.vendor.VendorService;
import com.marketplace.marketplacecommon.dto.vendor.VendorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void receiveVendor(@RequestBody VendorDto vendorDto) {
        vendorService.receiveVendor(vendorDto);
    }

}
