package com.ecommerce.marketplacein.controller;

import com.ecommerce.marketplacein.service.order.OrderUpdateStatusService;
import com.marketplace.marketplacecommon.dto.order.ConsignmentComissionDto;
import com.marketplace.marketplacecommon.dto.order.ConsignmentStatusUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderUpdateStatusService orderUpdateStatusService;

    @PostMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateConsignmentStatus(@RequestBody ConsignmentStatusUpdateDto consignmentStatusUpdateDto) throws ParseException {
        orderUpdateStatusService.updateConsignmentStatus(consignmentStatusUpdateDto);
    }

    @PostMapping("/comission")
    @ResponseStatus(HttpStatus.OK)
    public void saveComission(@RequestBody ConsignmentComissionDto consignmentComissionDto) throws ParseException {
        orderUpdateStatusService.saveComission(consignmentComissionDto);
    }


}
