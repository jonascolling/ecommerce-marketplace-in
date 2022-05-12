package com.ecommerce.marketplacein.service.order;

import com.ecommerce.marketplacein.replication.CentralSellerOrderReplication;
import com.marketplace.marketplacecommon.dto.ecommerceorder.*;
import com.marketplace.marketplacecommon.dto.order.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class ReceiveOrderServiceImpl implements ReceiveOrderService {

    private static final String PERSON = "PERSON";
    private static final String NORMAL = "NORMAL";
    private static final String DDD = "00";
    private static final String COR = "COR";
    private static final String COLOR = "COLOR";
    private static final int SIZE_LIMIT_NAME_FIELD = 40;
    private static final int SIZE_LIMIT_COMPLEMENT_FIELD = 60;

    @Autowired
    private CentralSellerOrderReplication centralSellerOrderReplication;

    @Override
    public void receiveOrder(EcommerceConsignmentDto ecommerceConsignmentDto) throws IOException {
        OrderDto orderDto = populateOrderDto(ecommerceConsignmentDto);
        centralSellerOrderReplication.postCentralSellerOrder(orderDto);
    }

    private OrderDto populateOrderDto(EcommerceConsignmentDto ecommerceConsignmentDto) {
        EcommerceOrderDto ecommerceOrderDto = ecommerceConsignmentDto.getOrder();

        OrderDto orderDto = new OrderDto();
        orderDto.setEcommerceOrderCode(ecommerceOrderDto.getCode());
        orderDto.setEcommerceConsignmentCode(ecommerceConsignmentDto.getCode());
        //orderDto.setChannelType(ecommerceOrderDto.get);
        orderDto.setCreationTime(ecommerceConsignmentDto.getCreationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        orderDto.setMaximumDeliveryDays(ecommerceConsignmentDto.getDaysToDelivery());
        orderDto.setSellerId(Long.parseLong(ecommerceConsignmentDto.getWarehouse().getVendor().getExternalId()));
        orderDto.setTotalPrice(ecommerceConsignmentDto.getTotalPrice());
        orderDto.setCostProducts(ecommerceConsignmentDto.getSubtotal());
        orderDto.setCostDiscount(ecommerceConsignmentDto.getDiscountAmount());
        orderDto.setShippingValue(ecommerceConsignmentDto.getCostOfDelivery());

        if (StringUtils.isNotEmpty(ecommerceConsignmentDto.getDeliveryType())) {
            orderDto.setShippingType(ecommerceConsignmentDto.getDeliveryType());
        } else {
            orderDto.setShippingType(NORMAL);
        }

        ecommerceOrderDto.getPaymentTransactions().stream().findFirst().ifPresent(paymentTransactionModel -> {
            orderDto.setSplitFlow("Pagarme");
        });

        EcommerceCustomerDto ecommerceCustomerDto = ecommerceOrderDto.getUser();
        orderDto.setCustomer(getCustomer(ecommerceCustomerDto));

        EcommerceAddressDto shippingAddress = ecommerceConsignmentDto.getShippingAddress();
        orderDto.setShippingAddressApiDto(getAddressToShipping(shippingAddress));

        List<ItemsOrderDto> itemsOrderDtos = new ArrayList<>();
        ecommerceConsignmentDto.getConsignmentEntries().forEach(consignmentEntryModel -> {
            itemsOrderDtos.add(getProductItems(consignmentEntryModel, ecommerceConsignmentDto));
        });
        orderDto.setItemsOrder(itemsOrderDtos);

        List<String> paymentType;
        if (OrderUtil.isCreditCard(ecommerceOrderDto)) {
            paymentType = Collections.singletonList(OrderUtil.CREDITCARD);
        } else if (OrderUtil.isBoleto(ecommerceOrderDto)) {
            paymentType = Collections.singletonList(OrderUtil.BOLETO);
        } else {
            paymentType = Collections.singletonList(OrderUtil.PIX);
        }
        orderDto.setPaymentTypes(paymentType);

        orderDto.setOrderPayment(Collections.singletonList(getPaymentFromOrder(ecommerceConsignmentDto)));
        return orderDto;
    }

    private OrderPaymentDto getPaymentFromOrder(EcommerceConsignmentDto ecommerceConsignmentDto) {
        OrderPaymentDto orderPaymentDto = new OrderPaymentDto();
        EcommerceOrderDto orderDto = ecommerceConsignmentDto.getOrder();

        orderDto.getPaymentTransactions().stream().findFirst().ifPresent(pt -> {
            orderPaymentDto.setCreationTime(pt.getCreationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            //orderPaymentDto.setNumberOrdersTransaction(orderDto.getConsignments().size());
            orderPaymentDto.setTransactionId(pt.getCode());
            orderPaymentDto.setPaymentValue(ecommerceConsignmentDto.getTotalPrice());
            orderPaymentDto.setTotalPaymentValue(pt.getPlannedAmount().doubleValue());
            orderPaymentDto.setEcommerceCardBrandId(NumberUtils.INTEGER_ONE);
            orderPaymentDto.setNumberInstallments(pt.getInstallments() != null ? pt.getInstallments().intValue() : NumberUtils.INTEGER_ONE);
            OrderUtil.populatePayment(orderPaymentDto, orderDto);
        });

        return orderPaymentDto;
    }

    private ItemsOrderDto getProductItems(EcommerceConsignmentEntryDto consignmentEntry, EcommerceConsignmentDto ecommerceConsignmentDto) {
        ItemsOrderDto itemOrderDto = new ItemsOrderDto();
        EcommerceOrderEntryDto orderEntry = consignmentEntry.getOrderEntry();
        EcommerceVariantProductDto variant = orderEntry.getProduct();
        EcommerceBaseProductDto baseProduct = variant.getBaseProduct();

        itemOrderDto.setBasePrice(orderEntry.getBasePrice());
        itemOrderDto.setTotalPrice(orderEntry.getTotalPrice());
        itemOrderDto.setColor(getColor(baseProduct));
        //itemOrderDto.setDiscount(getDiscountsFromOrderEntry(orderEntry));
        itemOrderDto.setProductId(Long.parseLong(baseProduct.getProductId()));
        itemOrderDto.setProductName(variant.getName());
        itemOrderDto.setSkuSellerCode(variant.getLegacySKU());
        itemOrderDto.setQuantity(orderEntry.getQuantity().intValue());
        itemOrderDto.setShippingValueItem(ecommerceConsignmentDto.getCostOfDelivery());
        itemOrderDto.setSize(variant.getSize());

        itemOrderDto.setProductData(getProductData(variant));

        return itemOrderDto;
    }

    private ProductDataDto getProductData(EcommerceVariantProductDto variant) {
        ProductDataDto productDataDto = new ProductDataDto();
        EcommerceBaseProductDto baseProduct = variant.getBaseProduct();

        productDataDto.setDeliveryTimeDays(variant.getDeliveryTimeDays() != null ? variant.getDeliveryTimeDays() : NumberUtils.INTEGER_ONE);
        productDataDto.setHeight(variant.getHeight() != null ? variant.getHeight() : NumberUtils.DOUBLE_ONE);
        productDataDto.setLength(variant.getLength() != null ? variant.getLength() : NumberUtils.DOUBLE_ONE);
        productDataDto.setWeight(variant.getWeight() != null ? variant.getWeight() : NumberUtils.DOUBLE_ONE);
        productDataDto.setWidth(variant.getWidth() != null ? variant.getWidth() : NumberUtils.DOUBLE_ONE);

        productDataDto.setProductItemId(Long.parseLong(variant.getProductItemId()));
        productDataDto.setProductItemSellerEcommerceId(variant.getPk());

        //Double itemSellerPrice = getArezzoCoProductService().getFullPrice(baseProduct);
        //productDataDto.setProductItemSellerPrice(itemSellerPrice);

        productDataDto.setUrlGuide("https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/c591462ef3ba44828588ac0000aa6a22_9366/Camisa_1_Juventus_20-21_(UNISSEX)_Branco_EI9894_25_outfit.jpg");
        productDataDto.setUrlImages(Collections.singleton("https://assets.adidas.com/images/h_840,f_auto,q_auto:sensitive,fl_lossy/c591462ef3ba44828588ac0000aa6a22_9366/Camisa_1_Juventus_20-21_(UNISSEX)_Branco_EI9894_25_outfit.jpg"));

        boolean containsCategoryLevel3 = baseProduct.getSupercategories().stream().anyMatch(c -> c.getBcMainCategory().equals(3));

        // Basic solution for send Order with category product.
        try {
            if (containsCategoryLevel3) {
                baseProduct.getSupercategories().stream()
                        .filter(c -> c.getBcMainCategory().equals(3))
                        .findFirst().ifPresent(c -> productDataDto.setCategoryCode(c.getCode()));
            } else {
                baseProduct.getSupercategories().stream()
                        .filter(c -> c.getBcMainCategory().equals(1) || c.getBcMainCategory().equals(2))
                        .findFirst().ifPresent(c -> productDataDto.setCategoryCode(c.getCode()));
            }
        } catch (Exception ex) {
            productDataDto.setCategoryCode("OTHER");
        }

        return productDataDto;
    }

    public static String getColor(final EcommerceBaseProductDto product) {
        Collection<EcommerceCategoryDto> categories = product.getSupercategories();

        for (final EcommerceCategoryDto category : categories) {
            final List<EcommerceCategoryDto> superCategories = category.getSupercategories();

            for (final EcommerceCategoryDto superCategory : superCategories) {
                if (StringUtils.equals(superCategory.getCode(), COR) || StringUtils.equals(superCategory.getCode(), COLOR)) {
                    return StringUtils.isNotEmpty(category.getName()) ? category.getName() : StringUtils.EMPTY;
                }
            }
        }

        return StringUtils.EMPTY;
    }

    private ShippingAddressDto getAddressToShipping(EcommerceAddressDto ecommerceAddressDto) {
        ShippingAddressDto shippingAddressDto = new ShippingAddressDto();

        shippingAddressDto.setCity(ecommerceAddressDto.getTown());
        shippingAddressDto.setStreet(ecommerceAddressDto.getStreetname());
        shippingAddressDto.setNumber(ecommerceAddressDto.getStreetnumber());
        shippingAddressDto.setNeighborhood(ecommerceAddressDto.getDistrict());
        shippingAddressDto.setComplement(getComplementWithSizeLimit(ecommerceAddressDto.getComplement()));
        shippingAddressDto.setPostCode(ecommerceAddressDto.getPostalcode());
        shippingAddressDto.setFederalState(ecommerceAddressDto.getRegion().getIsocodeShort());

        return shippingAddressDto;
    }

    private String getComplementWithSizeLimit(String complement) {
        return StringUtils.isNotEmpty(complement) && complement.length() > SIZE_LIMIT_COMPLEMENT_FIELD ?
                complement.substring(NumberUtils.INTEGER_ZERO, SIZE_LIMIT_COMPLEMENT_FIELD) : complement;
    }

    private CustomerDto getCustomer(EcommerceCustomerDto ecommerceCustomerDto) {
        CustomerDto customerDto = new CustomerDto();

        //customerDto.setEcommerceCustomerId(ecommerceCustomerDto.getPk() != null ? ecommerceCustomerDto.getPk().getLong() : null) ;
        customerDto.setCpf(ecommerceCustomerDto.getCpf());
        customerDto.setName(getNameWithSizeLimit(ecommerceCustomerDto.getName()));
        customerDto.setEmail(ecommerceCustomerDto.getContactEmail());
        ecommerceCustomerDto.getPhoneNumberList().stream()
                .findFirst()
                .ifPresent(phone -> {
                    customerDto.setDdd(StringUtils.isNotEmpty(phone.getAreaCode()) ? phone.getAreaCode() : DDD);
                    customerDto.setPhone(removeAreaCodeFromPhoneNumber(phone.getNumber()));
                });
        customerDto.setCustomerType(PERSON);

        return customerDto;
    }

    private String getNameWithSizeLimit(String name) {
        return StringUtils.isNotEmpty(name) && name.length() > SIZE_LIMIT_NAME_FIELD ?
                name.substring(NumberUtils.INTEGER_ZERO, SIZE_LIMIT_NAME_FIELD) : name;
    }

    public String removeAreaCodeFromPhoneNumber(final String phoneNumber) {
        final String phoneNumberSanitized = phoneNumber.replaceAll("[^0-9]", "");
        return phoneNumberSanitized.length() > 9 ? phoneNumberSanitized.substring(2) : phoneNumberSanitized;
    }

}
