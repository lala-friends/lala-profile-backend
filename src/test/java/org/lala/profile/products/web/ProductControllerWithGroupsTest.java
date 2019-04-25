package org.lala.profile.products.web;

import org.junit.jupiter.api.BeforeEach;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.products.repository.ProductRepository;
import org.lala.profile.products.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductControllerWithGroupsTest extends AbstractCommonTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void before() {
        Product product_1004 = Product.builder()
                .name("1004 name")
                .introduce("1004 introduce")
                .description("1004 description")
                .imageUrls(new String[]{})
                .color("red")
                .techs(new String[]{"java", "oracle"})
                .build();
        productRepository.save(product_1004);
    }


}
