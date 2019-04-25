package org.lala.profile.products.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.products.repository.ProductRepository;
import org.lala.profile.products.vo.Product;
import org.lala.profile.products.vo.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTests extends AbstractCommonTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void before() {
        Product product_1001 = Product.builder()
                .name("1001 name")
                .introduce("1001 introduce")
                .description("1001 description")
                .imageUrls(new String[]{})
                .color("red")
                .techs(new String[]{"java", "oracle"})
                .build();

        Product product_1002 = Product.builder()
                .name("1002 name")
                .introduce("1002 introduce")
                .description("1002 description")
                .imageUrls(new String[]{})
                .color("blue")
                .techs(new String[]{"python", "mysql"})
                .build();

        productRepository.save(product_1001);
        productRepository.save(product_1002);
    }

    @AfterEach
    void after() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("정상적으로 Product 를 저장하는 테스트")
    void given_correct_product_when_createProduct_then_return_created() throws Exception {

        ProductDto product = ProductDto.builder()
                .name("lala profile")
                .introduce("personal profile")
                .imageUrls(new String[]{"https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjem66J-dDgAhVQGKYKHVBKBTkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fkakaofriends%2F&psig=AOvVaw1nuQ1v4-gvK4Kac507Gl5o&ust=1550980050138484"})
                .techs(new String[]{"spring boot", "rest api", "react"})
                .color("red")
                .description("this is ling text")
                .build();

        mockMvc.perform(post("/api/products")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(product))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").isNotEmpty())
        ;
    }

    @Test
    @DisplayName("필수 입력값이 없는 경우 에러가 발생")
    void given_null_essential_value_when_createProduct_Bad_Request_Empty_Input() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .build();

        this.mockMvc.perform(post("/api/products")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(productDto))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력값이 잘못된 경우 에러가 발생 - image url 이 잘못된 경우")
    void given_wrong_imageUrls_when_createProduct_Bad_Request_Wrong_Input() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .name("lala profile")
                .introduce("personal profile")
                .imageUrls(new String[]{"aaa"}) // url 잘못입력
                .techs(new String[]{"spring boot", "rest api", "react"})
                .description("this is long text")
                .build();

        this.mockMvc.perform(post("/api/products")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(productDto))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
        ;
    }

    @Test
    @DisplayName("정상적으로 모든 Product 를 조회한다.")
    void findAllProduct() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<Product> allProductList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertFalse(CollectionUtils.isEmpty(allProductList), "allProductList is not empty");
    }

    @Test
    @DisplayName("인증정보를 포함하여 정상적으로 모든 Product 를 조회한다.")
    void findAllProductWithAuthentication() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/products")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List<Product> allProductList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertFalse(CollectionUtils.isEmpty(allProductList), "allProductList is not empty");
    }

    @Test
    @DisplayName("인증정보를 포함하여 정상적으로 Product 를 수정한다.")
    void given_oauth_when_putProduct_then_return_ok() throws Exception {
        ProductDto product_2001 = ProductDto.builder()
                .name("2001 name")
                .introduce("2001 introduce")
                .description("2001 description")
                .imageUrls(new String[]{})
                .color("grey")
                .techs(new String[]{"python", "mysql", "power point"})
                .build();

        MvcResult result = mockMvc.perform(post("/api/products")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(product_2001)))
                .andDo(print())
                .andReturn();

        Product targetProduct = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
        product_2001.setName("2001 name modify");
        product_2001.setIntroduce("2001 introduce modify");
        MvcResult result1 = mockMvc.perform(put("/api/products/" + targetProduct.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .content(this.objectMapper.writeValueAsString(product_2001)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Product modifiedProduct = objectMapper.readValue(result1.getResponse().getContentAsString(), Product.class);
        assertThat(modifiedProduct.getName()).isEqualTo(product_2001.getName());
        assertThat(modifiedProduct.getIntroduce()).isEqualTo(product_2001.getIntroduce());
    }
}
