package io.recruitment.assessment.api.controllers;

import io.recruitment.assessment.api.domain.Product;
import io.recruitment.assessment.api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ProductController.URL)
public class ProductController {

    static final String URL = "/products";

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("{id}")
    Map<String, String> getProduct(@PathVariable long id) {
        return Map.of("product", "Hi there!");
    }

    @PostMapping
    Product createProduct(@RequestBody final Product product) {
        return productService.createProduct(product);
    }
}
