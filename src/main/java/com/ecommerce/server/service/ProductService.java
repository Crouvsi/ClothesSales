package com.ecommerce.server.service;

import com.ecommerce.server.dto.product.ProductRequest;
import com.ecommerce.server.dto.product.ProductResponse;
import com.ecommerce.server.entity.Category;
import com.ecommerce.server.entity.Product;
import com.ecommerce.server.repository.CategoryRepository;
import com.ecommerce.server.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository; // Required to fetch category by ID

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.categoryId()));

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setDiscountPrice(request.discountPrice());
        product.setStock(request.stock());
        product.setCategory(category);
        product.setImageUrls(request.imageUrls());

        Product savedProduct = productRepository.save(product);
        return toResponse(savedProduct);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.categoryId()));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setDiscountPrice(request.discountPrice());
        product.setStock(request.stock());
        product.setCategory(category);
        product.setImageUrls(request.imageUrls());

        Product updatedProduct = productRepository.save(product);
        return toResponse(updatedProduct);
    }

    private ProductResponse toResponse(Product product) {
        Long categoryId = product.getCategory() == null ? null : product.getCategory().getId();
        String categoryName = product.getCategory() == null ? null : product.getCategory().getName();

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getDiscountPrice(),
                product.getStock(),
                categoryId,
                categoryName,
                product.getImageUrls(),
                product.getCreatedAt()
        );
    }
}