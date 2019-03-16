package org.lala.profile.products;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.common.AbstractCommonTest;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductProductDetailTest extends AbstractCommonTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Test
    @DisplayName("프로덕트를 조회하면 프로덕트 디테일이 함께 조회된다.")
    void given_product_when_selectProduct_then_return_product_with_productDetail() {
        Product product = Product.builder()
                .name("lala clipping")
                .introduce("clipped URL")
                .tech(new String[] {"java", "sql"})
                .build();

        ProductDetail productDetail = ProductDetail.builder()
                .title("lala clipping")
                .description("personal profile")
                .imageUrl("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjem66J-dDgAhVQGKYKHVBKBTkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fkakaofriends%2F&psig=AOvVaw1nuQ1v4-gvK4Kac507Gl5o&ust=1550980050138484")
                .product(product)
                .build();
        product.setProductDetail(productDetail);

        productRepository.save(product);
        productDetailRepository.save(productDetail);


    }
}
