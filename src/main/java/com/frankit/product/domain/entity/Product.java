package com.frankit.product.domain.entity;

import com.frankit.product.domain.dto.request.ProductRequestDto;
import com.frankit.product.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "products")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long productId;

    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal deliveryFee;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();

    @Builder
    public Product(String name, String description, BigDecimal price, BigDecimal deliveryFee) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.deliveryFee = deliveryFee;
        this.options = new ArrayList<>();
    }

    public static Product from(ProductRequestDto dto) {
        return Product.builder()
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .deliveryFee(dto.deliveryFee())
                .build();
    }

    public void update(ProductRequestDto dto) {
        this.name = dto.name();
        this.description = dto.description();
        this.price = dto.price();
        this.deliveryFee = dto.deliveryFee();
    }

    public void addOption(ProductOption option) {
        if (options.size() >= 3) {
            throw new IllegalStateException("상품 옵션은 최대 3개까지만 등록 가능합니다.");
        }

        options.add(option);
        option.setProduct(this);
    }
}
