package com.ecommerce.marketplacein.service.product;

import com.ecommerce.marketplacein.enums.ProductAttribute;
import com.ecommerce.marketplacein.replication.EcommerceProductReplication;
import com.ecommerce.marketplacein.service.product.utils.ProductUtil;
import com.marketplace.marketplacecommon.ecommerceproduct.dto.*;
import com.marketplace.marketplacecommon.product.dto.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private EcommerceProductReplication ecommerceProductReplication;

    @Override
    public void receiveProduct(ProductDTO productDTO) {
        List<EcommerceProductDTO> ecommerceProducts = populateEcommerceProductDto(productDTO);
        ecommerceProducts.forEach(productDto -> ecommerceProductReplication.postEcommerceProduct(productDto));
    }

    @Override
    public void updatePrice(ProductPriceUpdateDTO productPriceUpdateDTO) {
        ecommerceProductReplication.putEcommerceProductPrice(new EcommerceProductPriceUpdateDTO(productPriceUpdateDTO, ProductUtil.SELLER_MAP.get(productPriceUpdateDTO.getSellerId())));
    }

    @Override
    public void updateStock(ProductStockUpdateDTO productStockUpdateDTO) {
        ecommerceProductReplication.putEcommerceProductStock(new EcommerceProductStockUpdateDTO(productStockUpdateDTO, ProductUtil.SELLER_MAP.get(productStockUpdateDTO.getSellerId())));
    }

    @Override
    public void updateDeliveryData(ProductDeliveryDataUpdateDTO productDeliveryDataUpdateDTO) {
        ecommerceProductReplication.putEcommerceProductDeliveryData(new EcommerceProductDeliveryDataUpdateDTO(productDeliveryDataUpdateDTO, ProductUtil.SELLER_MAP.get(productDeliveryDataUpdateDTO.getSellerId())));
    }

    @Override
    public void updateStatus(ProductStatusUpdateDTO productStatusUpdateDTO) {
        ecommerceProductReplication.putEcommerceProductStatus(new EcommerceProductStatusUpdateDTO(productStatusUpdateDTO, ProductUtil.SELLER_MAP.get(productStatusUpdateDTO.getSellerId())));
    }

    private List<EcommerceProductDTO> populateEcommerceProductDto(ProductDTO productDTO) {
        List<String> colors = getColorVariations(productDTO);
        List<String> categories = getAttributeCategories(productDTO);

        List<EcommerceProductDTO> listOfBaseProducts = new ArrayList<>();

        if (CollectionUtils.isEmpty(colors) || colors.size() == 1) {
            EcommerceProductDTO ecommerceProductDTO = populateEcommerceBaseProduct(productDTO);

            List<ProductItemDTO> variants = productDTO.getProductItem();

            List<ProductItemPictureDTO> medias = variants.stream().findFirst().get().getProductItemPicture();
            List<EcommerceProductItemPictureDTO> ecommerceMedias = populateEcommercePictures(medias);
            ecommerceProductDTO.setProductItemPicture(ecommerceMedias);

            String colorCategory = colors.stream().findFirst().orElse(null);
            ecommerceProductDTO.setCategories(new ArrayList<>(categories));
            ecommerceProductDTO.getCategories().add(colorCategory);

            ecommerceProductDTO.setProductItem(populateEcommerceVariants(productDTO.getProductItem()));

            listOfBaseProducts.add(ecommerceProductDTO);
        } else {
            colors.forEach(color -> {
                EcommerceProductDTO ecommerceProductDTO = populateEcommerceBaseProduct(productDTO);

                List<ProductItemDTO> variants = ProductUtil.getVariantsByColor(productDTO, color);
                List<ProductItemPictureDTO> mediasOfThisColor = ProductUtil.getMediasByAnyVariant(variants);
                List<EcommerceProductItemPictureDTO> ecommerceMediasOfThisColor = populateEcommercePictures(mediasOfThisColor);
                ecommerceProductDTO.setProductItemPicture(ecommerceMediasOfThisColor);

                ecommerceProductDTO.setCategories(new ArrayList<>(categories));
                ecommerceProductDTO.getCategories().add(color);

                ecommerceProductDTO.setProductItem(populateEcommerceVariants(variants));

                listOfBaseProducts.add(ecommerceProductDTO);
            });
        }

        return listOfBaseProducts;
    }

    private List<EcommerceProductItemDTO> populateEcommerceVariants(List<ProductItemDTO> variants) {
        List<EcommerceProductItemDTO> ecommerceVariants = new ArrayList<>();

        variants.forEach(variant -> {
            EcommerceProductItemDTO ecommerceVariant = new EcommerceProductItemDTO();
            ecommerceVariant.setName(variant.getName());
            ecommerceVariant.setProductItemId(variant.getProductItemId());
            ecommerceVariant.setWeight(variant.getWeight());
            ecommerceVariant.setWidth(variant.getWidth());
            ecommerceVariant.setHeight(variant.getHeight());
            ecommerceVariant.setLength(variant.getLength());
            ecommerceVariant.setDeliveryTimeDays(variant.getDeliveryTimeDays());
            ecommerceVariant.setObsolete(variant.isObsolete());
            ecommerceVariant.setStock(variant.getStock());
            ecommerceVariant.setSize(variant.getSize());
            ecommerceVariant.setFullPrice(variant.getFullPrice());
            ecommerceVariant.setPrice(variant.getPrice());
            ecommerceVariant.setVariantSkuSellerId(variant.getVariantSkuSellerId());
            ecommerceVariant.setEan(variant.getEan());
            ecommerceVariants.add(ecommerceVariant);
        });

        return ecommerceVariants;
    }

    private EcommerceProductDTO populateEcommerceBaseProduct(ProductDTO productDTO) {

        List<ProductAttributeValueDTO> attributes = productDTO.getAttributeValue();
        String productHeelSizeString = ProductUtil.getAttribute(attributes, ProductAttribute.HEELSIZE.getAttribute());
        Double productHeelSize = Strings.isNotBlank(productHeelSizeString) ? Double.valueOf(productHeelSizeString) : null;
        String productBagDimension = ProductUtil.getAttribute(attributes, ProductAttribute.BAGDIMENSION.getAttribute());
        String productBagVolume = ProductUtil.getAttribute(attributes, ProductAttribute.BAGVOLUME.getAttribute());
        String sellerCode = ProductUtil.SELLER_MAP.get(productDTO.getSellerId());

        String productDescription = ProductUtil.removeHtmlTags(productDTO.getDescription());

        EcommerceProductDTO ecommerceProductDTO = new EcommerceProductDTO();
        ecommerceProductDTO.setName(productDTO.getName());
        ecommerceProductDTO.setDescription(productDescription);
        ecommerceProductDTO.setBrand(productDTO.getBrand());
        ecommerceProductDTO.setSellerCode(sellerCode);
        ecommerceProductDTO.setModel(productDTO.getModel());
        ecommerceProductDTO.setProductId(productDTO.getProductId());
        ecommerceProductDTO.setProductSkuSellerId(productDTO.getProductSkuSellerId());
        ecommerceProductDTO.setHeelSize(productHeelSize);
        ecommerceProductDTO.setBagDimension(productBagDimension);
        ecommerceProductDTO.setBagVolume(productBagVolume);
        return ecommerceProductDTO;

    }

    private List<EcommerceProductItemPictureDTO> populateEcommercePictures(List<ProductItemPictureDTO> medias) {

        List<EcommerceProductItemPictureDTO> ecommerceMedias = new ArrayList<>();
        medias.forEach(media -> {
            EcommerceProductItemPictureDTO ecommerceMedia = new EcommerceProductItemPictureDTO();
            ecommerceMedia.setUrl(media.getPicture());
            ecommerceMedia.setOrder(getOrderOfImage(media.getOrder().toString()));
            ecommerceMedias.add(ecommerceMedia);
        });

        return ecommerceMedias;
    }

    private String getOrderOfImage(String order) {
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
