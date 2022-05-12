package com.ecommerce.marketplacein.service.order;

import com.ecommerce.marketplacein.replication.EcommerceOrderReplication;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentComissionDto;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentStatusUpdateDto;
import com.marketplace.marketplacecommon.dto.order.ConsignmentComissionDto;
import com.marketplace.marketplacecommon.dto.order.ConsignmentStatusUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class OrderUpdateStatusServiceImpl implements OrderUpdateStatusService {

    @Autowired
    private EcommerceOrderReplication ecommerceOrderReplication;

    @Override
    public void updateConsignmentStatus(ConsignmentStatusUpdateDto consignmentStatusUpdateDto) throws ParseException {
        ecommerceOrderReplication.postEcommerceOrderStatus(populateEcommerceConsignmentStatusDto(consignmentStatusUpdateDto));
    }

    @Override
    public void saveComission(ConsignmentComissionDto consignmentComissionDto) throws ParseException {
        ecommerceOrderReplication.postEcommerceOrderComission(populateEcommerceConsignmentComissionDto(consignmentComissionDto));
    }

    public EcommerceConsignmentComissionDto populateEcommerceConsignmentComissionDto(ConsignmentComissionDto consignmentComissionDto) {
        EcommerceConsignmentComissionDto ecommerceConsignmentComissionDto = new EcommerceConsignmentComissionDto();
        ecommerceConsignmentComissionDto.setEcommerceConsignmentCode(consignmentComissionDto.getEcommerceConsignmentCode());
        ecommerceConsignmentComissionDto.setOrderCommissionTotal(consignmentComissionDto.getOrderCommissionTotal());
        return ecommerceConsignmentComissionDto;
    }

    private EcommerceConsignmentStatusUpdateDto populateEcommerceConsignmentStatusDto(ConsignmentStatusUpdateDto consignmentStatusUpdateDto) throws ParseException {
        EcommerceConsignmentStatusUpdateDto ecommerceConsignmentStatusUpdateDto = new EcommerceConsignmentStatusUpdateDto();
        ecommerceConsignmentStatusUpdateDto.setEcommerceConsignmentCode(consignmentStatusUpdateDto.getEcommerceConsignmentCode());
        ecommerceConsignmentStatusUpdateDto.setOrderReasonId(consignmentStatusUpdateDto.getOrderReasonId());
        ecommerceConsignmentStatusUpdateDto.setSellerId(consignmentStatusUpdateDto.getSellerId());
        ecommerceConsignmentStatusUpdateDto.setStatus(consignmentStatusUpdateDto.getStatus());
        ecommerceConsignmentStatusUpdateDto.setStatusDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(consignmentStatusUpdateDto.getStatusDate()));
        ecommerceConsignmentStatusUpdateDto.setOrderSellerInvoiceId(consignmentStatusUpdateDto.getOrderSellerInvoiceId());
        ecommerceConsignmentStatusUpdateDto.setInvoiceKey(consignmentStatusUpdateDto.getInvoiceKey());
        ecommerceConsignmentStatusUpdateDto.setInvoiceNumber(consignmentStatusUpdateDto.getInvoiceNumber());
        ecommerceConsignmentStatusUpdateDto.setInvoiceDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(consignmentStatusUpdateDto.getInvoiceDate()));
        ecommerceConsignmentStatusUpdateDto.setTrackingData(consignmentStatusUpdateDto.getTrackingData());
        ecommerceConsignmentStatusUpdateDto.setTrackingDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(consignmentStatusUpdateDto.getTrackingDate()));
        ecommerceConsignmentStatusUpdateDto.setSentTimestampEcommerce(consignmentStatusUpdateDto.getSentTimestampEcommerce());
        return ecommerceConsignmentStatusUpdateDto;
    }

}
