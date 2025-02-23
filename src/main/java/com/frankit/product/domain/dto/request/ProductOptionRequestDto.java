package com.frankit.product.domain.dto.request;

import com.frankit.product.domain.entity.OptionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ProductOptionRequestDto(
        Long productOptionId,

        @NotNull(message = "옵션명은 필수입니다")
        String name,

        @NotNull(message = "옵션타입은 필수입니다")
        OptionType type,

        @Size(max = 3, message = "상품 옵션은 최대 3개까지만 등록 가능합니다")
        List<String> optionValues,

        @NotNull(message = "상품명은 필수입니다")
        @PositiveOrZero(message = "가격은 0 이상이어야 합니다")
        BigDecimal additionalPrice
) {}
