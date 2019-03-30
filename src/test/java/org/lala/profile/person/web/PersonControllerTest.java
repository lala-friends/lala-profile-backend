package org.lala.profile.person.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.person.repository.PersonRepository;
import org.lala.profile.person.vo.Person;
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

public class PersonControllerTest extends AbstractCommonTest {

    @Autowired
    PersonRepository personRepository;

    @BeforeEach
    void before () {
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

    @Test
    @DisplayName("모든 Person 을 조회한다.")
    void getAllPerson() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/persons")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Person> personList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertFalse(CollectionUtils.isEmpty(personList), "PersonList is not empty");
    }
}
