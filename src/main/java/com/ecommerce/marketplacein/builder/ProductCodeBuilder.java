package com.ecommerce.marketplacein.builder;

import com.ecommerce.marketplacein.enums.GenericParams;
import com.ecommerce.marketplacein.service.keygenerator.KeyGeneratorService;
import com.ecommerce.marketplacein.service.product.utils.ProductUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductCodeBuilder {

    private static final List<String> internalSellers = new ArrayList<>(Arrays.asList("arezzo", "schutz"));

    @Autowired
    private KeyGeneratorService keyGeneratorService;

    public String getProductCode(String sellerCode, String productCode, String variantSize, String generatedProductCode) throws IllegalArgumentException {
        if (internalSellers.contains(sellerCode)) {
            String productCodeSuffix = sellerCode.substring(1).toUpperCase();

            if (Strings.isEmpty(variantSize)) {
                return productCode.endsWith(productCodeSuffix) ? productCode : productCode.concat(productCodeSuffix);
            }

            return productCode + "-" + variantSize;
        } else if (Strings.isNotBlank(variantSize)) {
            return generatedProductCode + "-" + ProductUtil.formattProductSize(variantSize.replace(",", "."));
        }

        return getExternalBaseProductCode();
    }

    private String getExternalBaseProductCode() {
        final String generatedValue = keyGeneratorService.getNextKeySequence(GenericParams.PRODUCT_CODE_KEY.getParam(), 13);
        return "M" + generatedValue + "U";
    }

}
