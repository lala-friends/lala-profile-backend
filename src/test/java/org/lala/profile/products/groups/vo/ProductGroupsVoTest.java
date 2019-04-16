package org.lala.profile.products.groups.vo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.accounts.repository.AccountsRepository;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.commons.AppProperties;
import org.lala.profile.person.repository.PersonRepository;
import org.lala.profile.person.vo.Person;
import org.lala.profile.products.repository.ProductRepository;
import org.lala.profile.products.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductGroupsVoTest extends AbstractCommonTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AppProperties properties;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @BeforeEach
    void before() {
        Product product_1003 = Product.builder()
                .name("1003 name")
                .introduce("1003 introduce")
                .description("1003 description")
                .imageUrls(new String[]{})
                .color("red")
                .techs(new String[]{"java", "oracle"})
                .build();
        productRepository.save(product_1003);

        Person person = Person.builder()
                .name("Ryan Woo")
                .email("ryan@naver.com")
                .introduce("PS4 and Gundam...")
                .imageUrl("https://firebasestorage.googleapis.com/v0/b/lala-profile-web.appspot.com/o/ryan.png?alt=media&token=c0808145-5312-4638-b57e-1b239c580022")
                .repColor("blue")
                .blog("https://ryanwoo.tistory.com/")
                .github("https://github.com/whuk")
                .facebook("https://www.facebook.com/profile.php?id=100001895042867")
                .build();
        personRepository.save(person);
    }

    @AfterEach
    void after() {
        productRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    @DisplayName("ProductGroups 빌더 테스트")
    public void builder() {

        Product product1 = productRepository.findAll().get(0);
        Person person1 = personRepository.findAll().get(0);

        ProductGroups groups = ProductGroups.builder()
                .person(person1)
                .product(product1)
                .isProductOn(true)
                .build();

        assertNotNull(groups, "groups is not null!");
    }
}
