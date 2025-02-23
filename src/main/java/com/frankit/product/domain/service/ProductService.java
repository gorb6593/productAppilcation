package com.frankit.product.domain.service;

import com.frankit.product.domain.dto.request.ProductRequestDto;
import com.frankit.product.domain.dto.response.ProductResponseDto;
import com.frankit.product.domain.entity.Product;
import com.frankit.product.domain.repository.ProductRepository;
import com.frankit.product.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;

import static com.frankit.product.global.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public void createProduct(ProductRequestDto requestDto) {
        Product product = Product.from(requestDto);
        productRepository.save(product);
    }

    public Page<ProductResponseDto> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponseDto::from);
    }

    @Transactional
    public void updateProduct(Long id, ProductRequestDto requestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        // 상품 정보 업데이트
        product.update(requestDto);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        productRepository.delete(product);
    }


}
