package org.lala.profile.products.groups.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.person.repository.PersonRepository;
import org.lala.profile.person.vo.Person;
import org.lala.profile.products.groups.vo.ProductGroups;
import org.lala.profile.products.repository.ProductRepository;
import org.lala.profile.products.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductGroupsRepositoryTest {
    @Autowired
    private ProductGroupsRepository productGroupsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("ProductGroups 저장 테스트")
    void productGroups_save_test() {
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

        ProductGroups productGroups = ProductGroups.builder()
                .isProductOn(true)
                .person(person)
                .product(product_1003)
                .build();
        product_1003.setProductGroups(Collections.singletonList(productGroups));

        // 이시점의 Person 의 ProductGroups 의 id == null
        Product getProductBeforeSave = productRepository.findAll().get(0);
        assertThat(getProductBeforeSave.getProductGroups().get(0).getId()).isNull();

        productGroupsRepository.save(productGroups);
        // productGroups 를 save 하는 시점에 relation 매핑
        Product getProductAfterSave = productRepository.findAll().get(0);
        assertThat(getProductAfterSave.getProductGroups().get(0).getId()).isNotNull();
    }
}
