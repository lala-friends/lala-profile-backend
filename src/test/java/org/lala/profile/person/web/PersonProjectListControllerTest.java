package org.lala.profile.person.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.accounts.repository.AccountsRepository;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.commons.AppProperties;
import org.lala.profile.person.repository.PersonRepository;
import org.lala.profile.person.vo.Person;
import org.lala.profile.person.vo.PersonWithProjects;
import org.lala.profile.person.vo.PersonWithProjectsDto;
import org.lala.profile.projects.repository.ProjectRepository;
import org.lala.profile.projects.vo.Project;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void before() {
        personRepository.deleteAll();

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
        Person adminPerson = Person.builder().email(email).build();
        personRepository.save(adminPerson);

        MvcResult mvcResult = mockMvc.perform(get("/api/persons/" + email + "/projects")
//                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
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

    @Test
    @DisplayName("인증정보가 있는 상태로 Person 을 수정하고 Project list 는 모두 지우고 새로 저장한다.")
    void given_personId_with_oauth_putPersonWithProjectList_then_putPerson_and_reCreateProjects_return_200() throws Exception {
        String email = appProperties.getAdminUsername();
        Account admin = accountsRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("admin account is not null!!"));
        Person origin = Person.builder().email(email).build();
        personRepository.save(origin);
//        Person origin = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("admin person is not null!!"));

        PersonWithProjectsDto personWithProjectsDto = new PersonWithProjectsDto();
        this.modelMapper.map(origin, personWithProjectsDto);
        personWithProjectsDto.setName("Ryan Woo");
        personWithProjectsDto.setBlog("https://ryanwoo.tistory.com/");

        List<Project> newProject = new ArrayList<>();

        Calendar startDt = new GregorianCalendar(2018, 0, 1);
        Calendar endDt = new GregorianCalendar(2018, 11, 31);
        Project project3 = Project.builder()
                .projectName("SI project3")
                .introduce("fucking SI project3")
                .periodFrom(startDt.getTime())
                .periodTo(endDt.getTime())
                .descriptions("fucking coding")
                .techs(new String[]{"java", "jsp", "oracle"})
                .personalRole("developer")
                .link("https://daum.net")
                .owner(admin)
                .build();
        newProject.add(project3);
        Project project4 = Project.builder()
                .projectName("SI project4")
                .introduce("Super fucking SI project4")
                .periodFrom(startDt.getTime())
                .periodTo(endDt.getTime())
                .descriptions("Super fucking coding")
                .techs(new String[]{"java", "jsp", "mysql"})
                .personalRole("PM")
                .link("https://naver.com")
                .owner(admin)
                .build();
        newProject.add(project4);
        personWithProjectsDto.setProjects(newProject);

        MvcResult mvcResult = mockMvc.perform(put("/api/persons/" + email)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .content(this.objectMapper.writeValueAsString(personWithProjectsDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("name").value("Ryan Woo"))
                .andExpect(jsonPath("blog").exists())
                .andExpect(jsonPath("blog").value("https://ryanwoo.tistory.com/"))
                .andExpect(jsonPath("projects[0].id").exists())
                .andExpect(jsonPath("projects[0].projectName").exists())
                .andExpect(jsonPath("projects[1].id").exists())
                .andExpect(jsonPath("projects[1].projectName").exists())
                .andReturn();

        PersonWithProjects personWithProjects = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonWithProjects.class);
        assertNotNull(personWithProjects);
        assertThat(personWithProjects.getEmail()).isEqualTo(email);
        assertThat(personWithProjects.getProjects()).isNotEmpty();
    }
}
