package com.hhplus.ecommerce.facade.product.dto;

import com.hhplus.ecommerce.domain.product.ProductEnums;
import com.hhplus.ecommerce.domain.product.entity.Product;
import com.hhplus.ecommerce.domain.product.entity.ProductOption;
import com.hhplus.ecommerce.domain.product.entity.ProductStock;

import java.io.Serializable;
import java.util.List;

public record FindProductResDto(
        Long productId,
        String name,
        ProductEnums.Type type,
        int price,
        int totalStock,
        List<FindProductOptionResDto> optionList

) implements Serializable {

    public record FindProductOptionResDto(
            Long productOptionId,
            ProductEnums.OptionType optionType,
            String name,
            String value,
            int price,
            int stock
    ) implements Serializable {
        public static FindProductOptionResDto fromOptions(ProductOption productOption, ProductStock productStock){
            return new FindProductOptionResDto(productOption.getProductOptionId(), productOption.getType(), productOption.getOptionName(),
                    productOption.getOptionValue(), productOption.getPrice(), productStock.getStock());
        }
    }

    public static FindProductResDto fromSingleProduct(Product product, ProductStock productStock){
        return new FindProductResDto(product.getProductId(), product.getName(), product.getType(), product.getPrice(),
                productStock.getStock(), null);
    }

    public static FindProductResDto fromOptionProduct(Product product, List<FindProductOptionResDto> optionList){
        int totalStock = optionList.stream().mapToInt(FindProductOptionResDto::stock).sum();

        return new FindProductResDto(product.getProductId(), product.getName(), product.getType(), product.getPrice(),
                totalStock, optionList);
    }
}
