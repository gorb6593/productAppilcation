package com.frankit.product.domain.dto.response;

import com.frankit.product.domain.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponseDto(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        BigDecimal deliveryFee,
        List<ProductOptionResponseDto> options,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getDeliveryFee(),
                product.getOptions().stream()
                        .map(ProductOptionResponseDto::from)
                        .toList(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
