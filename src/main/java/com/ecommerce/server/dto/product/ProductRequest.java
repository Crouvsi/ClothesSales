package com.ecommerce.server.dto.product;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(
        String name,
        String description,
        BigDecimal price,
        BigDecimal discountPrice,
        Integer stock,
        Long categoryId,
        List<String> imageUrls
) {}

