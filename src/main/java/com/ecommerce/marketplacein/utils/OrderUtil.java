package com.ecommerce.marketplacein.utils;

import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceOrderDto;
import com.marketplace.marketplacecommon.dto.order.OrderPaymentDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class OrderUtil {

    public static final String CREDITCARD = "CREDITCARD";
    public static final String BOLETO = "BOLETO";
    public static final String PIX = "PIX";

    public static boolean isBoleto(EcommerceOrderDto orderDto) {
        return orderDto.getPaymentMode().getCode().equals("Boleto");
    }

    public static boolean isCreditCard(EcommerceOrderDto orderDto) {
        return orderDto.getPaymentMode().getCode().equals("CreditCard");
    }

    public static void populatePayment(OrderPaymentDto orderPaymentDto, EcommerceOrderDto orderDto) {
        if (OrderUtil.isCreditCard(orderDto)) {
            orderPaymentDto.setEcommerceCardId(orderDto.getPaymentInfo().getPk());
            orderPaymentDto.setPayment(CREDITCARD);
            orderPaymentDto.setEcommerceCardBrandId(0);
        } else if (OrderUtil.isBoleto(orderDto)) {
            String ourNumber = GenericUtils.removeSpecialCharacters(orderDto.getPaymentInfo().getOurNumber());
            orderPaymentDto.setTicketNumber(StringUtils.isNotEmpty(ourNumber) ? Long.parseLong(ourNumber) : 0L);
            orderPaymentDto.setPayment(BOLETO);
        } else {
            orderPaymentDto.setPayment(PIX);
        }
    }

    public static HttpHeaders getEcommerceHeaderReques() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
