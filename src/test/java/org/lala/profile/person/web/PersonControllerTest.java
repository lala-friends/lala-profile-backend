package org.lala.profile.person.web;

import org.junit.jupiter.api.AfterEach;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonControllerTest extends AbstractCommonTest {

    @Autowired
    PersonRepository personRepository;

    @BeforeEach
    void before() {
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
        personRepository.deleteAll();
    }

    @Test
    @DisplayName("인증 정보가 있는 상태로 모든 Person 을 조회한다.")
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

    @Test
    @DisplayName("인증 정보가 없는 상태로 모든 Person 을 조회한다.")
    void getAllPerson_with_no_oauth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/persons")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        List<Person> personList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertFalse(CollectionUtils.isEmpty(personList), "PersonList is not empty");
    }

    @Test
    @DisplayName("인증정보가 있는 상태로 e-mail 로 Person 을 조회하면 person 이 리턴된다.")
    void given_person_email_with_oauth_when_getPerson_then_return_person() throws Exception {
        String email = "ryan@naver.com";

        MvcResult mvcResult = mockMvc.perform(get("/api/persons/" + email)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("name").value("Ryan Woo"))
                .andExpect(jsonPath("blog").exists())
                .andExpect(jsonPath("blog").value("https://ryanwoo.tistory.com/"))
                .andReturn();
        Person person = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Person.class);
        assertNotNull(person);
        assertThat(person.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("인증정보가 없는 상태로 e-mail 로 Person 을 조회하면 403 forbidden 이 리턴된다.")
    void given_person_email_with_no_oauth_when_getPerson_then_return_forbidden() throws Exception {
        String email = "ryan@naver.com";

        mockMvc.perform(get("/api/persons/" + email)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isForbidden())
        ;

    }
}
