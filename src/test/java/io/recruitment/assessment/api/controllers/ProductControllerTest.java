package io.recruitment.assessment.api.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getProduts_returnsOk() throws Exception {
        mockMvc.perform(get(ProductController.URL)).andExpect(status().isOk());
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