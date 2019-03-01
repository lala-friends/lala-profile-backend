//package org.lala.profile.products;
//
//import junitparams.JUnitParamsRunner;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(JUnitParamsRunner.class)
//public class ProductProductDetailTest {
//
//    @Test
//    public void productWithProductDetail() {
//        ProductDetail productDetail = ProductDetail.builder()
//                .id(10)
//                .title("lala profile detail")
//                .description("profile detail description")
//                .build();
//
//        Product product = Product.builder()
//                .name("lala profile")
//                .introduce("self description")
//                .productDetail(productDetail)
//                .build();
//
//        assertThat(product).isNotNull();
//    }
//}
