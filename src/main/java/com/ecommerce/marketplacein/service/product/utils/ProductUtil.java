package com.ecommerce.marketplacein.service.product.utils;

import com.ecommerce.marketplacein.enums.ProductAttribute;
import com.marketplace.marketplacecommon.product.dto.ProductAttributeValueDTO;
import com.marketplace.marketplacecommon.product.dto.ProductDTO;
import com.marketplace.marketplacecommon.product.dto.ProductItemDTO;
import com.marketplace.marketplacecommon.product.dto.ProductItemPictureDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductUtil {

    private static final Map<String, String> COMMON_STYLE = new HashMap<String, String>() {{
        put("slide", "SLIDE");
        put("birken", "BIRKEN");
        put("anabela", "ANABELA");
        put("meiapata", "MEIAPATA");
        put("ankleboot", "ANKLE_BOOT");
        put("plataforma", "PLATAFORMA");
        put("gladiadora", "GLADIADORA");
        put("overtheknee", "OVER_THE_KNEE");
        put("canoalto", "CANO_ALTO");
        put("saltotratorado", "SALTO_TRATORADO");
        put("saltobloco", "SALTO_BLOCO");
        put("saltofino", "SALTO_FINO");
        put("bicofino", "BICO_FINO");
        put("bicoquadrado", "BICO_QUADRADO");
        put("flat", "FLAT");
        put("flatform", "FLATFORM");
    }};

    public static final Map<String, String> SELLER_MAP = new HashMap<>() {{
        put("9", "arezzo");
        put("10", "schutz");
    }};

    public static List<ProductItemDTO> getVariantsByColor(ProductDTO productDto, String color) {
        return productDto.getProductItem().stream().filter(v -> v.getColor() != null && v.getColor().equals(color)).collect(Collectors.toList());
    }

    public static List<ProductItemPictureDTO> getMediasByAnyVariant(ProductDTO productDto, List<ProductItemDTO> variants) {
        ProductItemDTO variant = variants.stream().filter(v -> !CollectionUtils.isEmpty(v.getProductItemPicture())).findAny().orElse(null);
        return variant != null ? variant.getProductItemPicture() : null;
    }

    public static String removeHtmlTags(String text) {
        String regex = "<.*?>";
        if (Strings.isNotBlank(regex) && Strings.isNotBlank(text)) {
            return text.replaceAll(regex, "");
        }
        return text;
    }

    public static String formattProductSize(String size) {
        size = size.replace(" ", "-");
        size = removeAccents(size);
        return size.replaceAll("[^/0-9a-zA-Z--\\.]+", "");
    }

    public static String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", Strings.EMPTY);
    }

    public static void reorganizeProductMedias(List<ProductItemPictureDTO> medias) {
        medias.forEach(m -> m.setOrder(ProductUtil.getOrderOfImage(m.getOrder())));
    }

    public static String getOrderOfImage(String order) {
        if (Strings.isNotBlank(order)) {
            if (order.length() > 2) {
                return order.substring(order.length() - 2);
            }
            if (order.length() < 2) {
                return "0" + order;
            }
            return order;
        }
        return "01";
    }

    public static String getAttribute(List<ProductAttributeValueDTO> attributes, String attributeCode) {
        String attribute = Strings.EMPTY;
        if (!CollectionUtils.isEmpty(attributes)) {
            for (ProductAttributeValueDTO attr : attributes) {
                if (attributeCode.equalsIgnoreCase(attr.getAttributeEcommerceCode())) {
                    attribute = attr.getValue().toUpperCase();
                }
            }
        }
        return attribute;
    }

    public static List<String> getAttributes(ProductDTO productDTO, String... attributeLabels) {
        List<String> attributes = new ArrayList<>();
        boolean hasStyleAtt = false;

        if (!CollectionUtils.isEmpty(productDTO.getAttributeValue())) {
            for (String attributeLabel : attributeLabels) {
                for (ProductAttributeValueDTO attr : productDTO.getAttributeValue()) {
                    if (attr.getAttributeEcommerceCode().equalsIgnoreCase(attributeLabel)) {
                        attributes.add(attr.getValue().toUpperCase());
                    }
                }
            }

            String styleAttrCode = ProductAttribute.STYLE.getAttribute();
            hasStyleAtt = productDTO.getAttributeValue().stream()
                    .anyMatch(att -> att.getAttributeEcommerceCode().contains(styleAttrCode));
        }

        if (!hasStyleAtt) {
            verifyNameOfProductToAddStyle(attributes, productDTO);
        }

        return attributes;
    }

    private static void verifyNameOfProductToAddStyle(List<String> attributes, ProductDTO source) {
        String name = source.getProductItem().stream().findFirst().get().getName();
        if (Strings.isNotBlank(name)) {
            String productName = name.toLowerCase().trim();

            COMMON_STYLE.entrySet().stream()
                    .filter(k -> productName.contains(k.getKey()))
                    .findFirst()
                    .ifPresent(e -> {
                        attributes.add(e.getValue());
                    });
        }
    }

}
