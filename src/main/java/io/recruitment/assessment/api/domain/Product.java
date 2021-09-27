package io.recruitment.assessment.api.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.persistence.*;
import java.io.Serializable;

@JsonDeserialize(builder = Product.Builder.class)
@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String description;

    private Product(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Product() {
        // JPA
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static Builder aProduct() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private long id;
        private String description;

        private Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Product build() {
            return new Product(id, description);
        }
    }
}
