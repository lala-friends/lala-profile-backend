package org.lala.profile.products;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.common.AbstractCommonTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTests extends AbstractCommonTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void before() {
        Product product_1001 = Product.builder()
                .id(1001)
                .name("1001 name" )
                .introduce("1001 introduce")
                .descriptions("1001 description")
                .imageUrls(new String[] {})
                .color("red")
                .techs(new String[] {"java", "oracle"})
                .build();

        productRepository.save(product_1001);
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
                .descriptions("this is ling text")
                .build();

        mockMvc.perform(post("/products")
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

        this.mockMvc.perform(post("/products")
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
                .descriptions("this is long text")
                .build();

        this.mockMvc.perform(post("/products")
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
        this.mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())

        ;
    }
}
