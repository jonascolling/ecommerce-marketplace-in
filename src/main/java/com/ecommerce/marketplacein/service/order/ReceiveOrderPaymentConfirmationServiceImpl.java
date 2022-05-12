package com.ecommerce.marketplacein.service.order;

import com.ecommerce.marketplacein.replication.CentralSellerOrderReplication;
import com.ecommerce.marketplacein.utils.OrderUtil;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommerceConsignmentDto;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommercePaymentTransactionDto;
import com.marketplace.marketplacecommon.dto.ecommerceorder.EcommercePaymentTransactionEntryDto;
import com.marketplace.marketplacecommon.dto.order.PaymentConfirmationDto;
import com.marketplace.marketplacecommon.dto.order.PaymentContentDto;
import com.marketplace.marketplacecommon.dto.order.PaymentInstallmentDto;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ReceiveOrderPaymentConfirmationServiceImpl implements ReceiveOrderPaymentConfirmationService {

    @Autowired
    private CentralSellerOrderReplication centralSellerOrderReplication;

    @Override
    public void receiveOrderPaymentConfirmation(EcommerceConsignmentDto ecommerceConsignmentDto) throws IOException {
        PaymentConfirmationDto paymentConfirmationDto = new PaymentConfirmationDto();
        ecommerceConsignmentDto.getOrder().getPaymentTransactions().stream().findFirst().ifPresent(pt -> {
            paymentConfirmationDto.setEcommerceConsignmentCode(ecommerceConsignmentDto.getCode());
            paymentConfirmationDto.setPayments(getPayments(pt, ecommerceConsignmentDto));
        });

        centralSellerOrderReplication.postCentralSellerOrderPaymentConfirmation(paymentConfirmationDto);
    }

    private List<PaymentContentDto> getPayments(EcommercePaymentTransactionDto paymentTransactionDto, EcommerceConsignmentDto consignmentDto) {
        PaymentContentDto contentDto = new PaymentContentDto();

        contentDto.setCommissionTotalValue(consignmentDto.getCommissioningValueToEcommerce());
        contentDto.setCardTotalValue(getTotalCapturedFromTransaction(paymentTransactionDto, consignmentDto));
        contentDto.setTotalCaptured(paymentTransactionDto.getPlannedAmount().doubleValue());
        contentDto.setTransactionId(paymentTransactionDto.getCode());

        int installments = paymentTransactionDto.getInstallments() != null ? paymentTransactionDto.getInstallments().intValue() : NumberUtils.INTEGER_ONE;
        contentDto.setNumberInstallments(installments);

        contentDto.setNumberOrdersTransaction(consignmentDto.getOrder().getPaymentTransactions().size());
        contentDto.setEcommerceCardBrandId(NumberUtils.INTEGER_ONE);
        contentDto.setEcommerceAuthorization(paymentTransactionDto.getRequestId());
        contentDto.setInstallments(getInstallments(installments, consignmentDto));
        contentDto.setDateCaptured(getDateCaptured(paymentTransactionDto));

        if (OrderUtil.isCreditCard(consignmentDto.getOrder())) {
            contentDto.setPayment(OrderUtil.CREDITCARD);
        } else if (OrderUtil.isBoleto(consignmentDto.getOrder())) {
            String ourNumber = OrderUtil.removeSpecialCharacters(consignmentDto.getOrder().getPaymentInfo().getOurNumber());
            contentDto.setTicketNumber(Long.parseLong(ourNumber));
            contentDto.setPayment(OrderUtil.BOLETO);
        } else {
            contentDto.setPayment(OrderUtil.PIX);
        }

        return Collections.singletonList(contentDto);
    }

    private List<PaymentInstallmentDto> getInstallments(int installmentsCount, EcommerceConsignmentDto consignmentDto) {
        ArrayList<PaymentInstallmentDto> installments = new ArrayList<>();
        double installmentValue = new BigDecimal(consignmentDto.getTotalPrice() / installmentsCount).setScale(2,  RoundingMode.HALF_EVEN).doubleValue() ;
        for (int index=1; index <= installmentsCount; index++) {
            PaymentInstallmentDto installmentDto = new PaymentInstallmentDto();
            installmentDto.setCommissionValue(consignmentDto.getCommissioningValueToEcommerce());
            installmentDto.setInstallmentValue(installmentValue);
            installmentDto.setExpectedPaymentDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
            installmentDto.setInstallmentNumber(index);

            installments.add(installmentDto);
        }

        return installments;
    }

    private Double getTotalCapturedFromTransaction(EcommercePaymentTransactionDto paymentTransactionDto, EcommerceConsignmentDto consignmentDto) {
        EcommercePaymentTransactionEntryDto paymentTransactionEntryDto = getEntryFromTransaction(paymentTransactionDto);

        if (paymentTransactionEntryDto != null) {
            return paymentTransactionEntryDto.getAmount().doubleValue();
        }

        return consignmentDto.getTotalPrice();
    }

    private String getDateCaptured(EcommercePaymentTransactionDto paymentTransactionDto) {
        EcommercePaymentTransactionEntryDto entryFromTransaction = getEntryFromTransaction(paymentTransactionDto);

        if (entryFromTransaction != null) {
            return entryFromTransaction.getCreationtime()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private EcommercePaymentTransactionEntryDto getEntryFromTransaction(EcommercePaymentTransactionDto paymentTransactionDto) {
        return paymentTransactionDto.getEntries().stream()
                .filter(entry -> "CAPTURE".equals(entry.getType()))
                .findFirst()
                .orElse(null);
    }

}
