package com.ecommerce.marketplacein.service.vendor;

import com.ecommerce.marketplacein.replication.EcommerceVendorReplication;
import com.ecommerce.marketplacein.utils.GenericUtils;
import com.marketplace.marketplacecommon.dto.ecommercevendor.EcommerceAccountOfPaymentVendorDto;
import com.marketplace.marketplacecommon.dto.ecommercevendor.EcommerceVendorDto;
import com.marketplace.marketplacecommon.dto.vendor.VendorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private EcommerceVendorReplication ecommerceVendorReplication;

    @Override
    public void receiveVendor(VendorDto vendorDto) {
        ecommerceVendorReplication.postEcommerceVendor(populateEcommerceVendorDto(vendorDto));
    }

    private EcommerceVendorDto populateEcommerceVendorDto(VendorDto vendorDto) {
        EcommerceVendorDto ecommerceVendorDto = new EcommerceVendorDto();
        ecommerceVendorDto.setActive(vendorDto.isActive());
        ecommerceVendorDto.setApiFreight(vendorDto.getApiFreight());
        ecommerceVendorDto.setCep(vendorDto.getCep());
        ecommerceVendorDto.setCity(vendorDto.getCity());
        ecommerceVendorDto.setCpfCnpj(vendorDto.getCpfCnpj());
        ecommerceVendorDto.setName(vendorDto.getName());
        ecommerceVendorDto.setNumber(vendorDto.getNumber());
        ecommerceVendorDto.setStreet(vendorDto.getStreet());
        ecommerceVendorDto.setCode(getCodeOfVendor(vendorDto.getName()));

        EcommerceAccountOfPaymentVendorDto ecommerceAccountOfPaymentVendorDto = new EcommerceAccountOfPaymentVendorDto();
        ecommerceAccountOfPaymentVendorDto.setAccountBankAccountNumber(vendorDto.getAccount().getAccountBankAccountNumber());
        ecommerceAccountOfPaymentVendorDto.setAccountBankAgencyCode(vendorDto.getAccount().getAccountBankAgencyCode());
        ecommerceAccountOfPaymentVendorDto.setAccountBankAgencyVerificationDigit(vendorDto.getAccount().getAccountBankAgencyVerificationDigit());
        ecommerceAccountOfPaymentVendorDto.setAccountBankCode(vendorDto.getAccount().getAccountBankCode());
        ecommerceAccountOfPaymentVendorDto.setAccountDocumentNumber(vendorDto.getAccount().getAccountDocumentNumber());
        ecommerceAccountOfPaymentVendorDto.setAccountLegalName(vendorDto.getAccount().getAccountLegalName());
        ecommerceAccountOfPaymentVendorDto.setAccountVerificationDigits(vendorDto.getAccount().getAccountVerificationDigits());

        ecommerceVendorDto.setAccount(ecommerceAccountOfPaymentVendorDto);

        return ecommerceVendorDto;
    }

    private String getCodeOfVendor(String name) {
        String nameWithoutAccent = GenericUtils.removeAccents(name);
        String nameWithoutSpecialChar = GenericUtils.removeSpecialCharacters(nameWithoutAccent);

        return nameWithoutSpecialChar.replaceAll(" ", "_").toLowerCase();
    }

}
