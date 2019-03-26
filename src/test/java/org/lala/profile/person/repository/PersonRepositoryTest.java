package org.lala.profile.person.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.person.vo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("PersonRepository 저장 테스트")
    void personRepository_save_test() {
        String testEmail = "ryan@naver.com";
        Person person = Person.builder()
                .name("Ryan Woo")
                .email(testEmail)
                .introduce("PS4 and Gundam...")
                .imageUrl("https://firebasestorage.googleapis.com/v0/b/lala-profile-web.appspot.com/o/ryan.png?alt=media&token=c0808145-5312-4638-b57e-1b239c580022")
                .repColor("blue")
                .blog("https://ryanwoo.tistory.com/")
                .github("https://github.com/whuk")
                .facebook("https://www.facebook.com/profile.php?id=100001895042867")
                .build();

        personRepository.save(person);

        Optional<Person> byEmail = personRepository.findByEmail(person.getEmail());
        assertNotNull(byEmail.get(), "It must be not null!");
        assertThat(byEmail.get().getEmail()).isEqualTo(testEmail);
    }
}
