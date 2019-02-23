package org.lala.profile.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    public void createProduct() throws Exception {
        ProductDto product = ProductDto.builder()
                            .name("lala profile")
                            .introduce("personal profile")
                            .imageUrl("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjem66J-dDgAhVQGKYKHVBKBTkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fkakaofriends%2F&psig=AOvVaw1nuQ1v4-gvK4Kac507Gl5o&ust=1550980050138484")
                            .tech(new String[] {"spring boot", "rest api", "react"})
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
    public void createProduct_bad_request() throws Exception {
        Product product = Product.builder()
                            .id(10)
                            .name("lala profile")
                            .introduce("personal profile")
                            .imageUrl("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjem66J-dDgAhVQGKYKHVBKBTkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fkakaofriends%2F&psig=AOvVaw1nuQ1v4-gvK4Kac507Gl5o&ust=1550980050138484")
                            .tech(new String[] {"spring boot", "rest api", "react"})
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
}
