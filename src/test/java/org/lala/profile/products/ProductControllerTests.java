package org.lala.profile.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ProductControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductRepository productRepository;

    @Test
    public void createProduct() throws Exception {
        Product product = Product.builder()
                            .name("lala profile")
                            .introduce("personal profile")
                            .imageUrl("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwjem66J-dDgAhVQGKYKHVBKBTkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fkakaofriends%2F&psig=AOvVaw1nuQ1v4-gvK4Kac507Gl5o&ust=1550980050138484")
                            .tech(new String[] {"spring boot", "rest api", "react"})
                            .build();

        product.setId(10);
        Mockito.when(productRepository.save(product)).thenReturn(product);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(product))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
        ;
    }
}
