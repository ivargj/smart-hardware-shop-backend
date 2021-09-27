package io.recruitment.assessment.api.repository;

import io.recruitment.assessment.api.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
