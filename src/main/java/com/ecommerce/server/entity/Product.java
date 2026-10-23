package com.ecommerce.server.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "discount_price", precision = 10, scale = 2)
    private BigDecimal discountPrice;

    @Column(nullable = false)
    private Integer stock = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Product() {}

    public Product(
            String name,
            String description,
            BigDecimal price,
            BigDecimal discountPrice,
            Integer stock,
            Category category,
            List<String> imageUrls
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.stock = stock;
        this.category = category;
        this.imageUrls = imageUrls;
    }

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (stock == null) {
            stock = 0;
        }
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getDiscountPrice() { return discountPrice; }
    public Integer getStock() { return stock; }
    public Category getCategory() { return category; }
    public List<String> getImageUrls() { return imageUrls; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setDiscountPrice(BigDecimal discountPrice) { this.discountPrice = discountPrice; }
    public void setStock(Integer stock) { this.stock = stock; }
    public void setCategory(Category category) { this.category = category; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}