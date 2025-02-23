package com.frankit.product.domain.controler;

import com.frankit.product.domain.dto.request.ProductRequestDto;
import com.frankit.product.domain.dto.response.ProductPageResponse;
import com.frankit.product.domain.dto.response.ProductResponseDto;
import com.frankit.product.domain.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductRequestDto requestDto) {
        log.info("createProduct :: {}", requestDto);
        productService.createProduct(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ProductPageResponse<ProductResponseDto>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("getProducts :: {}, {}", page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductResponseDto> products = productService.getProducts(pageRequest);
        return ResponseEntity.ok(ProductPageResponse.from(products));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDto requestDto) {
        log.info("updateProduct :: id={}, request={}", id, requestDto);
        productService.updateProduct(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("deleteProduct :: id={}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
