package com.frankit.product.domain.service;

import com.frankit.product.domain.dto.request.ProductOptionRequestDto;
import com.frankit.product.domain.entity.OptionType;
import com.frankit.product.domain.entity.Product;
import com.frankit.product.domain.entity.ProductOption;
import com.frankit.product.domain.repository.ProductOptionRepository;
import com.frankit.product.domain.repository.ProductRepository;
import com.frankit.product.global.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductOptionServiceTest {

    @InjectMocks
    private ProductOptionService productOptionService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("옵션 생성 성공")
    void createOption_Success() {
        // given
        Long productId = 1L;
        Product product = createProduct();
        List<ProductOptionRequestDto> requestDtos = List.of(
                createOptionRequestDto("옵션1", OptionType.SELECT),
                createOptionRequestDto("옵션2", OptionType.INPUT)
        );
        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        // when
        productOptionService.createOption(productId, requestDtos);

        // then
        assertThat(product.getOptions()).hasSize(2);
        assertThat(product.getOptions().get(0).getName()).isEqualTo("옵션1");
        assertThat(product.getOptions().get(1).getName()).isEqualTo("옵션2");
    }

    @Test
    @DisplayName("옵션 수정 성공")
    void updateOption_Success() {
        // given
        Long productId = 1L;
        Product product = createProduct();

        // ProductOption 생성 시 Builder 사용
        ProductOption existingOption = ProductOption.builder()
                .name("기존 옵션")
                .type(OptionType.SELECT)
                .optionValues(List.of("값1", "값2"))
                .additionalPrice(BigDecimal.valueOf(1000))
                .build();

        // ID는 리플렉션을 사용해서 설정 (테스트용)
        ReflectionTestUtils.setField(existingOption, "productOptionId", 1L);

        product.addOption(existingOption);

        List<ProductOptionRequestDto> requestDtos = List.of(
                new ProductOptionRequestDto(
                        1L,  // 위에서 설정한 ID와 동일하게
                        "수정된 옵션",
                        OptionType.SELECT,
                        List.of("값1", "값2"),
                        BigDecimal.valueOf(1000)
                )
        );

        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        // when
        productOptionService.updateOption(productId, requestDtos);

        // then
        assertThat(existingOption.getName()).isEqualTo("수정된 옵션");
    }

    private Product createProduct() {
        return Product.builder()
                .name("테스트 상품")
                .price(BigDecimal.valueOf(10000))
                .deliveryFee(BigDecimal.valueOf(3000))
                .build();
    }

    private ProductOptionRequestDto createOptionRequestDto(String name, OptionType type) {
        return new ProductOptionRequestDto(
                null,
                name,
                type,
                List.of("값1", "값2"),
                BigDecimal.valueOf(1000)
        );
    }
}