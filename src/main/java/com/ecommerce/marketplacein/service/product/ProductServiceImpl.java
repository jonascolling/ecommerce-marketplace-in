package com.ecommerce.marketplacein.service.product;

import com.ecommerce.marketplacein.builder.ProductCodeBuilder;
import com.ecommerce.marketplacein.enums.ProductAttribute;
import com.ecommerce.marketplacein.exception.InvalidProductException;
import com.ecommerce.marketplacein.service.product.utils.ProductUtil;
import com.marketplace.marketplacecommon.product.dto.ProductAttributeValueDTO;
import com.marketplace.marketplacecommon.product.dto.ProductDTO;
import com.marketplace.marketplacecommon.product.dto.ProductItemDTO;
import com.marketplace.marketplacecommon.product.dto.ProductItemPictureDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCodeBuilder productCodeBuilder;

    @Override
    public void receiveProduct(ProductDTO productDTO) {

        List<String> colors = getColorVariations(productDTO);
        List<String> categories = getAttributeCategories(productDTO);

        List<ProductAttributeValueDTO> attributes = productDTO.getAttributeValue();
        String productHeelSizeString = ProductUtil.getAttribute(attributes, ProductAttribute.HEELSIZE.getAttribute());
        Double productHeelSize = Strings.isNotBlank(productHeelSizeString) ? Double.valueOf(productHeelSizeString) : null;
        String productBagDimension = ProductUtil.getAttribute(attributes, ProductAttribute.BAGDIMENSION.getAttribute());
        String productBagVolume = ProductUtil.getAttribute(attributes, ProductAttribute.BAGVOLUME.getAttribute());
        String sellerCode = ProductUtil.SELLER_MAP.get(productDTO.getSellerId());

        String productDescription = ProductUtil.removeHtmlTags(productDTO.getDescription());
        String productCode = productCodeBuilder.getProductCode(sellerCode, productDTO.getProductSkuSellerId(), null, null);

        if (CollectionUtils.isEmpty(colors) || colors.size() == 1) {
            List<ProductItemDTO> variants = productDTO.getProductItem();
            String colorCategory = colors.stream().findFirst().orElse(null);
            List<ProductItemPictureDTO> medias = variants.stream().findFirst().get().getProductItemPicture();
            ProductUtil.reorganizeProductMedias(medias);

            //createProduct
        } else {
            colors.forEach(color -> {
                List<ProductItemDTO> variants = ProductUtil.getVariantsByColor(productDTO, color);
                List<ProductItemPictureDTO> mediasOfThisColor = ProductUtil.getMediasByAnyVariant(productDTO, variants);
                ProductUtil.reorganizeProductMedias(mediasOfThisColor);

                //createProductPerColor
            });
        }


    }

    private List<String> getAttributeCategories(ProductDTO productDTO) {
        List<String> attributeCategories = new ArrayList<>();

        String materialAttrCode = ProductAttribute.MATERIAL.getAttribute();
        String skinAttrCode = ProductAttribute.SKINTYPE.getAttribute();
        String styleAttrCode = ProductAttribute.STYLE.getAttribute();

        List<String> materialStyleAndSkinTypeAttributes = ProductUtil.getAttributes(productDTO, materialAttrCode, styleAttrCode, skinAttrCode);
        if (!CollectionUtils.isEmpty(materialStyleAndSkinTypeAttributes)) {
            attributeCategories.addAll(materialStyleAndSkinTypeAttributes);
        }

        String gender = productDTO.getGender();
        if (Strings.isNotBlank(gender)) {
            attributeCategories.add(gender);
        }

        String groupEcommerceCode = productDTO.getGroupEcommerceCode();
        if (Strings.isNotBlank(groupEcommerceCode)) {
            attributeCategories.add(groupEcommerceCode);
        }

        return attributeCategories;
    }

    private List<String> getColorVariations(ProductDTO productDTO) {
        List<String> colors = new ArrayList<>();

        productDTO.getProductItem().forEach(i -> {
            if (Strings.isNotBlank(i.getColor()) && !colors.contains(i.getColor())) {
                colors.add(i.getColor());
            }
        });

        return colors;
    }

}
