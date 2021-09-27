package io.recruitment.assessment.api;

import io.recruitment.assessment.api.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

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
        Product product = postProduct(Product.aProduct().description(description).build());
        assertThat(product).isNotNull();
        assertThat(product.getDescription()).isEqualTo(description);
    }

    private Product postProduct(Product product) {
        return this.restTemplate.postForObject(urlForResource("/products"), product, Product.class);
    }

    private String urlForResource(String resource) {
        return "http://localhost:" + port + resource;
    }
}
