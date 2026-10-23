package com.ecommerce.server.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        BigDecimal discountPrice,
        Integer stock,
        Long categoryId,
        String categoryName,
        List<String> imageUrls,
        LocalDateTime createdAt
) {}