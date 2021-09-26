package io.recruitment.assessment.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ProductController.URL)
public class ProductController {

    static final String URL = "/products";

    @GetMapping
    List<Map<String, String>> getProduts() {
        return Collections.singletonList(Map.of("product", "Hi there!"));
    }

    @GetMapping("{id}")
    Map<String, String> getProdut(@PathVariable long id) {
        return Map.of("product", "Hi there!");
    }
}
