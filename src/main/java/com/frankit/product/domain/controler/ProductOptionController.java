package com.frankit.product.domain.controler;

import com.frankit.product.domain.dto.request.ProductOptionRequestDto;
import com.frankit.product.domain.dto.response.ProductOptionResponseDto;
import com.frankit.product.domain.service.ProductOptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/products/{productId}/options")
@RequiredArgsConstructor
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @PostMapping
    public ResponseEntity<Void> createOption(
            @PathVariable Long productId,
            @RequestBody @Valid List<ProductOptionRequestDto> requestDto
    ) {
        log.info("createOption :: productId={}, request={}", productId, requestDto);
        productOptionService.createOption(productId, requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductOptionResponseDto>> getOption(@PathVariable Long productId) {
        log.info("getOption :: productId={}", productId);
        List<ProductOptionResponseDto> option = productOptionService.getOption(productId);
        return ResponseEntity.ok(option);
    }

    @PatchMapping
    public ResponseEntity<Void> updateOption(
            @PathVariable Long productId,
            @RequestBody @Valid List<ProductOptionRequestDto> requestDto
    ) {
        log.info("updateOption :: productId={}, request={}", productId, requestDto);
        productOptionService.updateOption(productId, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(
            @PathVariable Long productId,
            @PathVariable Long optionId
    ) {
        log.info("deleteOption :: productId={}, optionId={}", productId, optionId);
        productOptionService.deleteOption(optionId);
        return ResponseEntity.ok().build();
    }
}
