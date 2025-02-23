package com.frankit.product.domain.dto.response;

import com.frankit.product.domain.entity.OptionType;
import com.frankit.product.domain.entity.ProductOption;

import java.math.BigDecimal;
import java.util.List;

public record ProductOptionResponseDto(
        Long productOptionId,
        String name,
        OptionType type,
        List<String> optionValues,
        BigDecimal additionalPrice
) {
    public static ProductOptionResponseDto from(ProductOption option) {
        return new ProductOptionResponseDto(
                option.getProductOptionId(),
                option.getName(),
                option.getType(),
                option.getOptionValues(),
                option.getAdditionalPrice()
        );
    }
}
