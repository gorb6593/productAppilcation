package com.frankit.product.domain.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record ProductPageResponse<T>(
        long totalElements,
        int totalPages,
        int number,
        int size,
        List<T> content,
        boolean first,
        boolean last
) {
    public static <T> ProductPageResponse<T> from(Page<T> page) {
        return new ProductPageResponse<>(
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.getContent(),
                page.isFirst(),
                page.isLast()
        );
    }
}
