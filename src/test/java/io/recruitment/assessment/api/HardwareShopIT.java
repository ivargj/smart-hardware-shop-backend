package io.recruitment.assessment.api;

import io.recruitment.assessment.api.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HardwareShopIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenPostingAProduct_shouldReturnCreatedProduct() {
        String description = "A describing description";
        Double price = 13.37;
        Product product = postProduct(Product.aProduct()
                .description(description)
                .price(price)
                .build());

        assertThat(product).isNotNull();
        assertThat(product.getId()).isGreaterThan(0L);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getPrice()).isEqualTo(price);
    }

    @Nested
    class GivenACreatedProduct {

        private Product product;

        @BeforeEach
        void createProduct() {
            product = postProduct(Product.aProduct()
                    .description("Really cool thing")
                    .price(42.0)
                    .build());
        }

        @Test
        void getAllProducts_shouldContainProduct() {
            List<Product> products = getProducts();
            assertThat(products).extracting(Product::getDescription).contains(product.getDescription());
            assertThat(products).extracting(Product::getPrice).contains(product.getPrice());
        }

        @Test
        void getProductById_shouldContainProduct() {
            ResponseEntity<Product> productResponse = getProduct(String.valueOf(this.product.getId()));
            assertThat(productResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(productResponse.getBody()).satisfies(p -> {
                assertThat(p.getDescription()).isEqualTo((this.product.getDescription()));
                assertThat(p.getPrice()).isEqualTo((this.product.getPrice()));
            });
        }

        @Test
        void getProductByOtherId_shouldReturnNotFound() {
            assertThat(getProduct(String.valueOf(this.product.getId() + 10)).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    private Product postProduct(Product product) {
        return this.restTemplate.postForObject(urlForResource("/products"), product, Product.class);
    }

    private List<Product> getProducts() {
        ParameterizedTypeReference<List<Product>> responseType = new ParameterizedTypeReference<>() {
        };
        return this.restTemplate.exchange(urlForResource("/products"), HttpMethod.GET, null, responseType).getBody();
    }

    private ResponseEntity<Product> getProduct(String id) {
        return this.restTemplate.exchange(urlForResource("/products") + "/" + id, HttpMethod.GET, null, Product.class);
    }

    private String urlForResource(String resource) {
        return "http://localhost:" + port + resource;
    }
}
