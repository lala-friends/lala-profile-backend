package org.lala.profile.person.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.person.repository.PersonRepository;
import org.lala.profile.person.vo.Person;
import org.lala.profile.products.groups.repository.ProductGroupsRepository;
import org.lala.profile.products.groups.vo.ProductGroups;
import org.lala.profile.products.repository.ProductRepository;
import org.lala.profile.products.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonControllerWithProductTest extends AbstractCommonTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductGroupsRepository productGroupsRepository;

    @BeforeEach
    void before() {
        Person person = Person.builder()
                .name("Ryan Woo")
                .email("frodo@naver.com")
                .introduce("PS4 and Gundam...")
                .imageUrl("https://firebasestorage.googleapis.com/v0/b/lala-profile-web.appspot.com/o/ryan.png?alt=media&token=c0808145-5312-4638-b57e-1b239c580022")
                .repColor("blue")
                .blog("https://ryanwoo.tistory.com/")
                .github("https://github.com/whuk")
                .facebook("https://www.facebook.com/profile.php?id=100001895042867")
                .keywords(new String[]{"IU", "Girls Generation"})
                .build();

        personRepository.save(person);

        Product product_1001 = Product.builder()
                .name("1001 name")
                .introduce("1001 introduce")
                .description("1001 description")
                .imageUrls(new String[]{})
                .color("red")
                .techs(new String[]{"java", "oracle"})
                .build();

        ProductGroups productGroups_1001 = ProductGroups.builder()
                .isProductOn(true)
                .person(person)
                .product(product_1001)
                .build();
        productRepository.save(product_1001);
        productGroupsRepository.save(productGroups_1001);


        Product product_1002 = Product.builder()
                .name("1002 name")
                .introduce("1002 introduce")
                .description("1002 description")
                .imageUrls(new String[]{})
                .color("blue")
                .techs(new String[]{"python", "mysql"})
                .build();

        ProductGroups productGroups_1002 = ProductGroups.builder()
                .isProductOn(true)
                .person(person)
                .product(product_1002)
                .build();

        productRepository.save(product_1002);
        productGroupsRepository.save(productGroups_1002);
    }

    @AfterEach
    void after() {
        productGroupsRepository.deleteAll();
        productRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    @DisplayName("Person 의 모든 Products 를 조회한다.")
    void given_person_product_when_get_persons_product_then_return_products() throws Exception {
        String email = "frodo@naver.com";

        MvcResult mvcResult = mockMvc.perform(get("/api/persons/" + email + "/products")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List productList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertFalse(CollectionUtils.isEmpty(productList), "PersonList is not empty");
    }
}
