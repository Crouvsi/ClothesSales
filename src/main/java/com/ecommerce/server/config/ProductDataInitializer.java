package com.ecommerce.server.config;

import com.ecommerce.server.entity.Category;
import com.ecommerce.server.entity.Product;
import com.ecommerce.server.repository.CategoryRepository;
import com.ecommerce.server.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Loads a small starter catalog for local/demo environments.
 * Existing categories and products are preserved; each item is inserted only once.
 */
@Component
public class ProductDataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductDataInitializer(
            CategoryRepository categoryRepository,
            ProductRepository productRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Map<String, Category> categories = new HashMap<>();
        for (Category category : categoryRepository.findAll()) {
            categories.put(category.getName(), category);
        }

        Category men = getOrCreateCategory("Men", categories);
        Category women = getOrCreateCategory("Women", categories);
        Category footwear = getOrCreateCategory("Footwear", categories);
        Category accessories = getOrCreateCategory("Accessories", categories);

        Set<String> existingNames = new HashSet<>();
        for (Product product : productRepository.findAll()) {
            existingNames.add(product.getName());
        }

        addProduct(existingNames, "Classic Cotton T-Shirt",
                "A versatile everyday crew-neck tee made from soft cotton.",
                "19.90", "14.90", 45, men,
                "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?auto=format&fit=crop&w=900&q=80");

        addProduct(existingNames, "Essential Pullover Hoodie",
                "A comfortable mid-weight hoodie with a relaxed fit and adjustable drawstring hood.",
                "49.90", "39.90", 28, men,
                "https://images.unsplash.com/photo-1556821840-3a63f95609a7?auto=format&fit=crop&w=900&q=80");

        addProduct(existingNames, "Slim Fit Denim Jeans",
                "Classic five-pocket denim jeans with a modern slim fit.",
                "59.90", null, 32, men,
                "https://images.unsplash.com/photo-1542272604-787c3835535d?auto=format&fit=crop&w=900&q=80");

        addProduct(existingNames, "Lightweight Bomber Jacket",
                "A clean everyday bomber jacket with a full front zip and practical pockets.",
                "89.90", "74.90", 16, men,
                "https://images.unsplash.com/photo-1591047139829-d91aecb6caea?auto=format&fit=crop&w=900&q=80");

        addProduct(existingNames, "Floral Summer Dress",
                "A breezy floral dress designed for warm days and casual occasions.",
                "54.90", "44.90", 21, women,
                "https://images.unsplash.com/photo-1595777457583-95e059d581b8?auto=format&fit=crop&w=900&q=80");

        addProduct(existingNames, "Soft Knit Sweater",
                "A soft, easy-to-layer knit sweater with a timeless silhouette.",
                "64.90", null, 19, women,
                "https://images.unsplash.com/photo-1576566588028-4147f3842f27?auto=format&fit=crop&w=900&q=80");

        addProduct(existingNames, "Everyday White Sneakers",
                "Minimal low-top sneakers that pair easily with casual outfits.",
                "69.90", "59.90", 24, footwear,
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=900&q=80");

        addProduct(existingNames, "Classic Leather-Style Boots",
                "An everyday ankle boot with a clean profile and sturdy sole.",
                "99.90", null, 12, footwear,
                "https://images.unsplash.com/photo-1608256246200-53e635b5b65f?auto=format&fit=crop&w=900&q=80");

        addProduct(existingNames, "Everyday Shoulder Bag",
                "A compact shoulder bag with room for daily essentials.",
                "44.90", "34.90", 18, accessories,
                "https://images.unsplash.com/photo-1548036328-c9fa89d128fa?auto=format&fit=crop&w=900&q=80");

        addProduct(existingNames, "Minimalist Baseball Cap",
                "A lightweight adjustable cap for relaxed everyday styling.",
                "24.90", null, 36, accessories,
                "https://images.unsplash.com/photo-1588850561407-ed78c282e89b?auto=format&fit=crop&w=900&q=80");
    }

    private Category getOrCreateCategory(String name, Map<String, Category> categories) {
        Category category = categories.get(name);
        if (category == null) {
            category = categoryRepository.save(new Category(name));
            categories.put(name, category);
        }
        return category;
    }

    private void addProduct(
            Set<String> existingNames,
            String name,
            String description,
            String price,
            String discountPrice,
            int stock,
            Category category,
            String imageUrl
    ) {
        if (!existingNames.add(name)) {
            return;
        }

        Product product = new Product(
                name,
                description,
                new BigDecimal(price),
                discountPrice == null ? null : new BigDecimal(discountPrice),
                stock,
                category,
                List.of(imageUrl)
        );
        productRepository.save(product);
    }
}
