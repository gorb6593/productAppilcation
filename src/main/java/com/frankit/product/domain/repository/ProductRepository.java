package com.frankit.product.domain.repository;

import com.frankit.product.domain.entity.Product;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @NonNull
    @Override
    Page<Product> findAll(@NonNull Pageable pageable);
}
