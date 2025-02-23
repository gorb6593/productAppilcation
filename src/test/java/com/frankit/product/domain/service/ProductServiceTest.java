package com.frankit.product.domain.service;

import com.frankit.product.domain.dto.request.ProductRequestDto;
import com.frankit.product.domain.dto.response.ProductResponseDto;
import com.frankit.product.domain.entity.Product;
import com.frankit.product.domain.repository.ProductRepository;
import com.frankit.product.global.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 생성 성공")
    void createProduct_Success() {
        // given
        ProductRequestDto requestDto = new ProductRequestDto(
                "테스트 상품",
                "테스트 설명",
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(3000)
        );
        Product product = Product.from(requestDto);
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        productService.createProduct(requestDto);

        // then
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 목록 조회 성공")
    void getProducts_Success() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> products = List.of(
                createProduct("상품1", BigDecimal.valueOf(10000)),
                createProduct("상품2", BigDecimal.valueOf(20000))
        );
        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());
        given(productRepository.findAll(pageable)).willReturn(productPage);

        // when
        Page<ProductResponseDto> result = productService.getProducts(pageable);

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).name()).isEqualTo("상품1");
        assertThat(result.getContent().get(1).name()).isEqualTo("상품2");
    }

    @Test
    @DisplayName("상품 수정 성공")
    void updateProduct_Success() {
        // given
        Long productId = 1L;
        ProductRequestDto requestDto = new ProductRequestDto(
                "수정된 상품",
                "수정된 설명",
                BigDecimal.valueOf(20000),
                BigDecimal.valueOf(3000)
        );
        Product product = createProduct("기존 상품", BigDecimal.valueOf(10000));
        given(productRepository.findById(productId)).willReturn(Optional.of(product));

        // when
        productService.updateProduct(productId, requestDto);

        // then
        assertThat(product.getName()).isEqualTo("수정된 상품");
        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(20000));
    }

    @Test
    @DisplayName("상품 수정 실패 - 존재하지 않는 상품")
    void updateProduct_Fail_NotFound() {
        // given
        Long productId = 1L;
        ProductRequestDto requestDto = new ProductRequestDto(
                "수정된 상품",
                "수정된 설명",
                BigDecimal.valueOf(20000),
                BigDecimal.valueOf(3000)
        );
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> productService.updateProduct(productId, requestDto))
                .isInstanceOf(NotFoundException.class);
    }

    private Product createProduct(String name, BigDecimal price) {
        return Product.from(new ProductRequestDto(
                name,
                "설명",
                price,
                BigDecimal.valueOf(3000)
        ));
    }
}