package com.ecommerce.marketplacein.enums;

public enum ProductAttribute {

    HEELSIZE("heelSize"),
    BAGDIMENSION("bagDimension"),
    BAGVOLUME("bagVolume"),
    MATERIAL("material"),
    STYLE("style"),
    SKINTYPE("skinType");

    private final String attribute;

    ProductAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
