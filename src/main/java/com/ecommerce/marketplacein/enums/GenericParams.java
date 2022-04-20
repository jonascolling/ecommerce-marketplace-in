package com.ecommerce.marketplacein.enums;

public enum GenericParams {

    PRODUCT_CODE_KEY("PRODUCT_CODE");

    private final String param;

    GenericParams(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

}
