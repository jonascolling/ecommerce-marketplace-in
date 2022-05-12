package com.ecommerce.marketplacein.controller;

import com.marketplace.marketplacecommon.dto.order.ConsignmentComissionDto;
import com.marketplace.marketplacecommon.dto.order.ConsignmentDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateConsignmentStatus(@RequestBody ConsignmentDto consignmentDto) {

    }

    @PostMapping("/comission")
    @ResponseStatus(HttpStatus.OK)
    public void saveComission(@RequestBody ConsignmentComissionDto consignmentComissionDto) {

    }


}
