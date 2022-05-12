package com.ecommerce.marketplacein.service.order;

import com.marketplace.marketplacecommon.dto.order.ConsignmentComissionDto;
import com.marketplace.marketplacecommon.dto.order.ConsignmentStatusUpdateDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;

public interface OrderUpdateStatusService {

    void updateConsignmentStatus(@RequestBody ConsignmentStatusUpdateDto consignmentStatusUpdateDto) throws ParseException;

    void saveComission(@RequestBody ConsignmentComissionDto consignmentComissionDto) throws ParseException;

}
