package com.frankit.product.domain.service;

import com.frankit.product.domain.dto.request.ProductOptionRequestDto;
import com.frankit.product.domain.dto.response.ProductOptionResponseDto;
import com.frankit.product.domain.entity.Product;
import com.frankit.product.domain.entity.ProductOption;
import com.frankit.product.domain.repository.ProductOptionRepository;
import com.frankit.product.domain.repository.ProductRepository;
import com.frankit.product.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.frankit.product.global.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOptionService {
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public void createOption(Long productId, List<ProductOptionRequestDto> requestDto) {
        Product product = findProduct(productId);

        requestDto.forEach(optionDto -> {
            ProductOption option = ProductOption.from(optionDto);
            product.addOption(option);
        });
    }

    public List<ProductOptionResponseDto> getOption(Long productId) {
        Product product = findProduct(productId);

        return product.getOptions().stream()
                .map(ProductOptionResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateOption(Long productId, List<ProductOptionRequestDto> requestDto) {
        Product product = findProduct(productId);

        requestDto.forEach(optionDto -> {
            if (optionDto.productOptionId() != null) {
                // 기존 옵션 업데이트
                ProductOption existingOption = product.getOptions().stream()
                        .filter(opt -> opt.getProductOptionId().equals(optionDto.productOptionId()))
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException(NOT_FOUND));

                existingOption.update(optionDto);
            } else {
                // 새로운 옵션 추가
                ProductOption newOption = ProductOption.from(optionDto);
                product.addOption(newOption);
            }
        });
    }

    @Transactional
    public void deleteOption(Long optionId) {
        ProductOption option = findOption(optionId);
        option.getProduct().getOptions().remove(option);
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    private ProductOption findOption(Long optionId) {
        return productOptionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }
}
