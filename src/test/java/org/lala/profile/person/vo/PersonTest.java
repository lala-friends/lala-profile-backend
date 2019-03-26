package org.lala.profile.person.vo;

import junitparams.JUnitParamsRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitParamsRunner.class)
class PersonTest {

    @Test
    @DisplayName("Person 빌더 테스트")
    void builder() {
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
        assertNotNull(person, "Person is not null!");
    }

}