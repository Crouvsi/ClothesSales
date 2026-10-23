package com.ecommerce.server.controller;

import com.ecommerce.server.dto.product.ProductRequest;
import com.ecommerce.server.dto.product.ProductResponse;
import com.ecommerce.server.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Public endpoint: Anyone can view products
    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.findAll();
    }

    // Admin only: Add a new product
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse createdProduct = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // Admin only: Modify/Update an existing product
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(updatedProduct);
    }
}