package org.lala.profile.products;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    public void builder() {
        Product project = Product.builder()
                .name("lala clipping")
                .introduce("clipped URL")
                .build();

        assertThat(project).isNotNull();
    }

    @Test
    public void javaBean() {
        // Given
        String name = "lala profile";
        String introduce = "personal profile";

        // When
        Product product = new Product();
        product.setName(name);
        product.setIntroduce(introduce);

        // Then
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getIntroduce()).isEqualTo(introduce);
    }
}