package com.frankit.product.domain.entity;

import com.frankit.product.domain.dto.request.ProductOptionRequestDto;
import com.frankit.product.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "product_options")
public class ProductOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long productOptionId;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;

    @Enumerated(EnumType.STRING)
    private OptionType type;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "option_values", joinColumns = @JoinColumn(name = "option_id"))
    private List<String> optionValues = new ArrayList<>();

    private BigDecimal additionalPrice;

    @Builder
    public ProductOption(String name, OptionType type, List<String> optionValues, BigDecimal additionalPrice) {
        this.name = name;
        this.type = type;
        this.optionValues = optionValues;
        this.additionalPrice = additionalPrice;
    }

    public static ProductOption from(ProductOptionRequestDto dto) {
        return ProductOption.builder()
                .name(dto.name())
                .type(dto.type())
                .optionValues(dto.optionValues())
                .additionalPrice(dto.additionalPrice())
                .build();
    }

    public void update(ProductOptionRequestDto dto) {
        this.name = dto.name();
        this.type = dto.type();
        this.optionValues = dto.optionValues();
        this.additionalPrice = dto.additionalPrice();
    }

}
