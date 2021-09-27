package io.recruitment.assessment.api.services;

import io.recruitment.assessment.api.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    public List<Product> getProducts() {
        return Collections.singletonList(Product.aProduct().build());
    }

    public Product createProduct(Product product) {
        return product;
    }
}
