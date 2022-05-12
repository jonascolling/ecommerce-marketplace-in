package com.ecommerce.marketplacein.service.product;

import com.ecommerce.marketplacein.enums.ProductAttribute;
import com.ecommerce.marketplacein.replication.EcommerceProductReplication;
import com.ecommerce.marketplacein.utils.ProductUtil;
import com.marketplace.marketplacecommon.dto.ecommerceproduct.*;
import com.marketplace.marketplacecommon.dto.product.*;
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
    public void receiveProduct(ProductDto productDTO) {
        List<EcommerceProductDto> ecommerceProducts = populateEcommerceProductDto(productDTO);
        ecommerceProducts.forEach(productDto -> ecommerceProductReplication.postEcommerceProduct(productDto));
    }

    @Override
    public void updatePrice(ProductPriceUpdateDto productPriceUpdateDTO) {
        ecommerceProductReplication.putEcommerceProductPrice(populateEcommerceProductPriceUpdateDto(productPriceUpdateDTO, ProductUtil.SELLER_MAP.get(productPriceUpdateDTO.getSellerId())));
    }

    @Override
    public void updateStock(ProductStockUpdateDto productStockUpdateDTO) {
        ecommerceProductReplication.putEcommerceProductStock(populateEcommerceProductStockUpdateDto(productStockUpdateDTO, ProductUtil.SELLER_MAP.get(productStockUpdateDTO.getSellerId())));
    }

    @Override
    public void updateDeliveryData(ProductDeliveryDataUpdateDto productDeliveryDataUpdateDTO) {
        ecommerceProductReplication.putEcommerceProductDeliveryData(populateEcommerceProductDeliveryDataUpdateDto(productDeliveryDataUpdateDTO, ProductUtil.SELLER_MAP.get(productDeliveryDataUpdateDTO.getSellerId())));
    }

    @Override
    public void updateStatus(ProductStatusUpdateDto productStatusUpdateDTO) {
        ecommerceProductReplication.putEcommerceProductStatus(populateEcommerceProductStatusUpdateDto(productStatusUpdateDTO, ProductUtil.SELLER_MAP.get(productStatusUpdateDTO.getSellerId())));
    }

    private EcommerceProductStatusUpdateDto populateEcommerceProductStatusUpdateDto(ProductStatusUpdateDto productStatusUpdateDTO, String sellerCode) {
        EcommerceProductStatusUpdateDto ecommerceProductStatusUpdateDto = new EcommerceProductStatusUpdateDto();
        ecommerceProductStatusUpdateDto.setObsolete(productStatusUpdateDTO.isObsolete());
        ecommerceProductStatusUpdateDto.setProductId(productStatusUpdateDTO.getProductId());
        ecommerceProductStatusUpdateDto.setSentTimestampEcommerce(productStatusUpdateDTO.getSentTimestampEcommerce());
        ecommerceProductStatusUpdateDto.setSkuSellerId(productStatusUpdateDTO.getSkuSellerId());
        ecommerceProductStatusUpdateDto.setSellerCode(sellerCode);
        ecommerceProductStatusUpdateDto.setProductId(productStatusUpdateDTO.getProductId());
        return ecommerceProductStatusUpdateDto;
    }

    private EcommerceProductDeliveryDataUpdateDto populateEcommerceProductDeliveryDataUpdateDto(ProductDeliveryDataUpdateDto productDeliveryDataUpdateDTO, String sellerCode) {
        EcommerceProductDeliveryDataUpdateDto ecommerceProductDeliveryDataUpdateDto = new EcommerceProductDeliveryDataUpdateDto();
        ecommerceProductDeliveryDataUpdateDto.setWeight(productDeliveryDataUpdateDTO.getWeight());
        ecommerceProductDeliveryDataUpdateDto.setLength(productDeliveryDataUpdateDTO.getLength());
        ecommerceProductDeliveryDataUpdateDto.setWeight(productDeliveryDataUpdateDTO.getWeight());
        ecommerceProductDeliveryDataUpdateDto.setWidth(productDeliveryDataUpdateDTO.getWidth());
        ecommerceProductDeliveryDataUpdateDto.setProductId(productDeliveryDataUpdateDTO.getProductId());
        ecommerceProductDeliveryDataUpdateDto.setSentTimestampEcommerce(productDeliveryDataUpdateDTO.getSentTimestampEcommerce());
        ecommerceProductDeliveryDataUpdateDto.setSkuSellerId(productDeliveryDataUpdateDTO.getSkuSellerId());
        ecommerceProductDeliveryDataUpdateDto.setSellerCode(sellerCode);
        ecommerceProductDeliveryDataUpdateDto.setProductId(productDeliveryDataUpdateDTO.getProductId());
        return ecommerceProductDeliveryDataUpdateDto;
    }

    private EcommerceProductStockUpdateDto populateEcommerceProductStockUpdateDto(ProductStockUpdateDto productStockUpdateDTO, String sellerCode) {
        EcommerceProductStockUpdateDto ecommerceProductStockUpdateDto = new EcommerceProductStockUpdateDto();
        ecommerceProductStockUpdateDto.setStock(productStockUpdateDTO.getStock());
        ecommerceProductStockUpdateDto.setProductId(productStockUpdateDTO.getProductId());
        ecommerceProductStockUpdateDto.setSentTimestampEcommerce(productStockUpdateDTO.getSentTimestampEcommerce());
        ecommerceProductStockUpdateDto.setSkuSellerId(productStockUpdateDTO.getSkuSellerId());
        ecommerceProductStockUpdateDto.setSellerCode(sellerCode);
        ecommerceProductStockUpdateDto.setProductId(productStockUpdateDTO.getProductId());
        return ecommerceProductStockUpdateDto;
    }

    private EcommerceProductPriceUpdateDto populateEcommerceProductPriceUpdateDto(ProductPriceUpdateDto productPriceUpdateDTO, String sellerCode) {
        EcommerceProductPriceUpdateDto ecommerceProductPriceUpdateDto = new EcommerceProductPriceUpdateDto();
        ecommerceProductPriceUpdateDto.setFullPrice(productPriceUpdateDTO.getFullPrice());
        ecommerceProductPriceUpdateDto.setPrice(productPriceUpdateDTO.getPrice());
        ecommerceProductPriceUpdateDto.setProductId(productPriceUpdateDTO.getProductId());
        ecommerceProductPriceUpdateDto.setSentTimestampEcommerce(productPriceUpdateDTO.getSentTimestampEcommerce());
        ecommerceProductPriceUpdateDto.setSkuSellerId(productPriceUpdateDTO.getSkuSellerId());
        ecommerceProductPriceUpdateDto.setSellerCode(sellerCode);
        ecommerceProductPriceUpdateDto.setProductId(productPriceUpdateDTO.getProductId());
        return ecommerceProductPriceUpdateDto;
    }

    private List<EcommerceProductDto> populateEcommerceProductDto(ProductDto productDTO) {
        List<String> colors = getColorVariations(productDTO);
        List<String> categories = getAttributeCategories(productDTO);

        List<EcommerceProductDto> listOfBaseProducts = new ArrayList<>();

        if (CollectionUtils.isEmpty(colors) || colors.size() == 1) {
            EcommerceProductDto ecommerceProductDTO = populateEcommerceBaseProduct(productDTO);

            List<ProductItemDto> variants = productDTO.getProductItem();

            List<ProductItemPictureDto> medias = variants.stream().findFirst().get().getProductItemPicture();
            List<EcommerceProductItemPictureDto> ecommerceMedias = populateEcommercePictures(medias);
            ecommerceProductDTO.setProductItemPicture(ecommerceMedias);

            String colorCategory = colors.stream().findFirst().orElse(null);
            ecommerceProductDTO.setCategories(new ArrayList<>(categories));
            ecommerceProductDTO.getCategories().add(colorCategory);

            ecommerceProductDTO.setProductItem(populateEcommerceVariants(productDTO.getProductItem()));

            listOfBaseProducts.add(ecommerceProductDTO);
        } else {
            colors.forEach(color -> {
                EcommerceProductDto ecommerceProductDTO = populateEcommerceBaseProduct(productDTO);

                List<ProductItemDto> variants = ProductUtil.getVariantsByColor(productDTO, color);
                List<ProductItemPictureDto> mediasOfThisColor = ProductUtil.getMediasByAnyVariant(variants);
                List<EcommerceProductItemPictureDto> ecommerceMediasOfThisColor = populateEcommercePictures(mediasOfThisColor);
                ecommerceProductDTO.setProductItemPicture(ecommerceMediasOfThisColor);

                ecommerceProductDTO.setCategories(new ArrayList<>(categories));
                ecommerceProductDTO.getCategories().add(color);

                ecommerceProductDTO.setProductItem(populateEcommerceVariants(variants));

                listOfBaseProducts.add(ecommerceProductDTO);
            });
        }

        return listOfBaseProducts;
    }

    private List<EcommerceProductItemDto> populateEcommerceVariants(List<ProductItemDto> variants) {
        List<EcommerceProductItemDto> ecommerceVariants = new ArrayList<>();

        variants.forEach(variant -> {
            EcommerceProductItemDto ecommerceVariant = new EcommerceProductItemDto();
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

    private EcommerceProductDto populateEcommerceBaseProduct(ProductDto productDTO) {

        List<ProductAttributeValueDto> attributes = productDTO.getAttributeValue();
        String productHeelSizeString = ProductUtil.getAttribute(attributes, ProductAttribute.HEELSIZE.getAttribute());
        Double productHeelSize = Strings.isNotBlank(productHeelSizeString) ? Double.valueOf(productHeelSizeString) : null;
        String productBagDimension = ProductUtil.getAttribute(attributes, ProductAttribute.BAGDIMENSION.getAttribute());
        String productBagVolume = ProductUtil.getAttribute(attributes, ProductAttribute.BAGVOLUME.getAttribute());
        String sellerCode = ProductUtil.SELLER_MAP.get(productDTO.getSellerId());

        String productDescription = ProductUtil.removeHtmlTags(productDTO.getDescription());

        EcommerceProductDto ecommerceProductDTO = new EcommerceProductDto();
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

    private List<EcommerceProductItemPictureDto> populateEcommercePictures(List<ProductItemPictureDto> medias) {

        List<EcommerceProductItemPictureDto> ecommerceMedias = new ArrayList<>();
        medias.forEach(media -> {
            EcommerceProductItemPictureDto ecommerceMedia = new EcommerceProductItemPictureDto();
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

    private List<String> getAttributeCategories(ProductDto productDTO) {
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

    private List<String> getColorVariations(ProductDto productDTO) {
        List<String> colors = new ArrayList<>();

        productDTO.getProductItem().forEach(i -> {
            if (Strings.isNotBlank(i.getColor()) && !colors.contains(i.getColor())) {
                colors.add(i.getColor());
            }
        });

        return colors;
    }

}
