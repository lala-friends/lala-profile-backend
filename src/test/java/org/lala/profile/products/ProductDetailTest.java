package org.lala.profile.products;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ProductDetailTest {

    @Test
    public void builder() {
        ProductDetail productDetail = ProductDetail.builder()
                .id(10)
                .title("lala profile")
                .description("personal profile")
                .imageUrl("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjem66J-dDgAhVQGKYKHVBKBTkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fkakaofriends%2F&psig=AOvVaw1nuQ1v4-gvK4Kac507Gl5o&ust=1550980050138484")
                .build();

        assertThat(productDetail).isNotNull();
    }
}
