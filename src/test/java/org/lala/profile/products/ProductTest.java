package org.lala.profile.products;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lala.profile.products.vo.Product;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
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
    @Parameters
    public void javaBean(String name, String introduce) {

        // When
        Product product = new Product();
        product.setName(name);
        product.setIntroduce(introduce);

        // Then
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getIntroduce()).isEqualTo(introduce);
    }

    // Given
    private Object[] parametersForJavaBean() {
        return new Object[]{
                new Object[]{"lala profile", "personal profile"}
        };
    }


}