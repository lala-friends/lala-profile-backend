package org.lala.profile.person.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.accounts.service.AccountsService;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.person.repository.PersonRepository;
import org.lala.profile.person.vo.Person;
import org.lala.profile.person.vo.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PersonControllerTest extends AbstractCommonTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AccountsService accountsService;

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
                .keywords(new String[] {"IU", "Girls Generation"})
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

        List personList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
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

        List personList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertFalse(CollectionUtils.isEmpty(personList), "PersonList is not empty");
    }

    @Test
    @DisplayName("인증정보가 있는 상태로 e-mail 로 Person 을 조회하면 person 이 리턴된다.")
    void given_person_email_with_oauth_when_getPerson_then_return_person() throws Exception {
        String email = "frodo@naver.com";

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
    @DisplayName("인증정보가 없는 상태로 e-mail 로 Person 을 조회하면 200 이 리턴된다.")
    void given_person_email_with_no_oauth_when_getPerson_then_return_200() throws Exception {
        String email = "frodo@naver.com";

        mockMvc.perform(get("/api/persons/" + email)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
        ;

    }

    @Test
    @DisplayName("인증정보가 없는 상태로 Person 을 수정하면 401 이 리턴된다.")
    void given_person_email_with_no_oauth_when_putPerson_then_return_401() throws Exception {
        String email = "frodo@naver.com";
        Person byEmail = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

        PersonDto personDto = PersonDto.builder()
                .name("apeach")
                .introduce("peach peach apeach")
                .blog("http://apeach.com")
                .facebook("https://facebook.com/apeach")
                .github("https://github.com/apeach")
                .repColor("pink")
                .imageUrl("https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjtlMfd-q7hAhWCE7wKHWXIDhAQjRx6BAgBEAU&url=https%3A%2F%2Ftenor.com%2Fsearch%2Fapeach-gifs&psig=AOvVaw3kpGNU7k9x8f3MlytpYlha&ust=1554210311145409")
                .build();

        mockMvc.perform(put("/api/persons/{id}" ,byEmail.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(personDto))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    // 다른 API 로 대체
//    @Test
//    @DisplayName("인증정보가 있는 상태로 Person 을 수정하면 정상적으로 수정된다.")
//    void given_person_email_with_oauth_when_putPerson_then_return_200() throws Exception {
//        String email = "frodo@naver.com";
//        Person byEmail = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
//
//        Account account = Account.builder()
//                .email(email)
//                .password("1q2w3e4r%")
//                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
//                .build();
//        accountsService.saveAccount(account);
//
//
//        PersonDto personDto = PersonDto.builder()
//                .name("apeach")
//                .introduce("peach peach apeach")
//                .blog("http://apeach.com")
//                .facebook("https://facebook.com/apeach")
//                .github("https://github.com/apeach")
//                .repColor("pink")
//                .imageUrl("https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjtlMfd-q7hAhWCE7wKHWXIDhAQjRx6BAgBEAU&url=https%3A%2F%2Ftenor.com%2Fsearch%2Fapeach-gifs&psig=AOvVaw3kpGNU7k9x8f3MlytpYlha&ust=1554210311145409")
//                .keywords(new String[] {"In the air", "k8s"})
//                .build();
//
//        // 테스트용 계정의 토큰
//        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
//                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
//                .param("username", email)
//                .param("password", "1q2w3e4r%")
//                .param("grant_type", "password"));
//        var responseBody = perform.andReturn().getResponse().getContentAsString();
//        Jackson2JsonParser jackson2JsonParser = new Jackson2JsonParser();
//        String token ="Bearer " + jackson2JsonParser.parseMap(responseBody).get("access_token").toString();
//
//        mockMvc.perform(put("/api/persons/{id}" ,byEmail.getId())
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .header(HttpHeaders.AUTHORIZATION, token)
//                .content(this.objectMapper.writeValueAsString(personDto))
//        )
//                .andDo(print())
//                .andExpect(status().isOk())
//        ;
//    }

}
