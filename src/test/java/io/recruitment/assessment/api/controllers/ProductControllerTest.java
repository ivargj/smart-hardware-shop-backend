package io.recruitment.assessment.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.recruitment.assessment.api.AppConfig;
import io.recruitment.assessment.api.domain.Product;
import io.recruitment.assessment.api.services.ProductService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ContextConfiguration(classes = AppConfig.class)
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @Autowired
    MockMvc mockMvc;

    @Nested
    class GetProducts {

        @Test
        void getProduts_returnsOk() throws Exception {
            mockMvc.perform(get(ProductController.URL)).andExpect(status().isOk());
        }

        @Test
        void getProduts_getsProducts() throws Exception {
            mockMvc.perform(get(ProductController.URL));

            verify(productService).getProducts();
        }

        @Test
        void getProdut_returnsOk() throws Exception {
            mockMvc.perform(get(ProductController.URL + "/123")).andExpect(status().isOk());
        }

        @Test
        void getProdutWithInvalidNumber_returnsBadRequest() throws Exception {
            mockMvc.perform(get(ProductController.URL + "/1B3")).andExpect(status().isBadRequest());
        }
    }

    @Nested
    class CreateProducts {

        @Test
        void createProduct_returnsOk() throws Exception {
            String description = "This is a great product!";
            returnProductWithDescription(description);

            Product product = Product.aProduct().description("Bla").build();
            postAsJson(product)
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().string(containsString(description)));

            verify(productService).createProduct(any(Product.class));
        }

        void returnProductWithDescription(String description) {
            when(productService.createProduct(any())).thenReturn(Product.aProduct().description(description).build());
        }

        private ResultActions postAsJson(Product product) throws Exception {
            return mockMvc.perform(post(ProductController.URL)
                    .content(asJsonString(product))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        }

    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}