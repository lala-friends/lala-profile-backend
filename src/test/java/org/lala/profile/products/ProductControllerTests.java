package org.lala.profile.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lala.profile.common.TestDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @TestDescription("정상적으로 Product를 저장하는 테스트")
    public void createProduct() throws Exception {
        ProductDto product = ProductDto.builder()
                .name("lala profile")
                .introduce("personal profile")
                .imageUrl("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjem66J-dDgAhVQGKYKHVBKBTkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fkakaofriends%2F&psig=AOvVaw1nuQ1v4-gvK4Kac507Gl5o&ust=1550980050138484")
                .tech(new String[]{"spring boot", "rest api", "react"})
                .build();

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(product))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("name").isNotEmpty())
        ;
    }

    @Test
    @TestDescription("입력 받을 수 없는 값이 들어온 경우 에러가 발생")
    public void createProduct_Bad_Request() throws Exception {
        Product product = Product.builder()
                .id(10)
                .name("lala profile")
                .introduce("personal profile")
                .imageUrl("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjem66J-dDgAhVQGKYKHVBKBTkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fkakaofriends%2F&psig=AOvVaw1nuQ1v4-gvK4Kac507Gl5o&ust=1550980050138484")
                .tech(new String[]{"spring boot", "rest api", "react"})
                .build();

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(product))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())

        ;
    }

    @Test
    @TestDescription("필수 입력값이 없는 경우 에러가 발생")
    public void createProduct_Bad_Request_Empty_Input() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .build();

        this.mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(productDto))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("입력값이 잘못된 경우 에러가 발생")
    public void createProduct_Bad_Request_Wrong_Input() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .name("lala profile")
                .introduce("personal profile")
                .imageUrl("aaa")
                .tech(new String[]{"spring boot", "rest api", "react"})
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
}
