package org.lala.profile.person.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.accounts.repository.AccountsRepository;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.commons.AppProperties;
import org.lala.profile.person.vo.PersonWithProjects;
import org.lala.profile.projects.repository.ProjectRepository;
import org.lala.profile.projects.vo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonProjectListControllerTest extends AbstractCommonTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AppProperties appProperties;

    @BeforeEach
    void before() {
        String admin = appProperties.getAdminUsername();
        Account byEmail = accountsRepository.findByEmail(admin).orElseThrow(() -> new UsernameNotFoundException(admin));

        Calendar startDt = new GregorianCalendar(2018, 0, 1);
        Calendar endDt = new GregorianCalendar(2018, 11, 31);

        Project project1 = Project.builder()
                .projectName("SI project1")
                .introduce("fucking SI project")
                .periodFrom(startDt.getTime())
                .periodTo(endDt.getTime())
                .descriptions("fucking coding")
                .techs(new String[]{"java", "jsp", "oracle"})
                .personalRole("developer")
                .link("https://daum.net")
                .owner(byEmail)
                .build();

        projectRepository.save(project1);

        Project project2 = Project.builder()
                .projectName("SI project2")
                .introduce("Super fucking SI project")
                .periodFrom(startDt.getTime())
                .periodTo(endDt.getTime())
                .descriptions("Super fucking coding")
                .techs(new String[]{"java", "jsp", "mysql"})
                .personalRole("PM")
                .link("https://naver.com")
                .owner(byEmail)
                .build();

        projectRepository.save(project2);
    }

    @AfterEach
    void after() {
        projectRepository.deleteAll();
    }

    @Test
    @DisplayName("인증정보가 없는 상태로 e-mail 로 Person 을 조회하면 person 과 project list 가 리턴된다.")
    void given_person_email_with_no_oauth_when_getPersonWithProjectList_then_return_person_and_projectList() throws Exception {
        String email = appProperties.getAdminUsername();

        MvcResult mvcResult = mockMvc.perform(get("/api/persons/" + email + "/projects")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("name").exists())
//                .andExpect(jsonPath("name").value("Ryan Woo"))
//                .andExpect(jsonPath("blog").exists())
//                .andExpect(jsonPath("blog").value("https://ryanwoo.tistory.com/"))
                .andReturn();
        PersonWithProjects personWithProjects = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonWithProjects.class);
        assertNotNull(personWithProjects);
        assertThat(personWithProjects.getEmail()).isEqualTo(email);
        assertThat(personWithProjects.getProjects()).isNotEmpty();
    }
}
