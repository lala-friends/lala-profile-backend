package org.lala.profile.projects.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.accounts.repository.AccountsRepository;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.commons.AppProperties;
import org.lala.profile.projects.repository.ProjectRepository;
import org.lala.profile.projects.vo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectControllerTest extends AbstractCommonTest {

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
    @DisplayName("인증정보 없이 모든 project 를 조회한다.")
    void given_no_oauth_getAllProjects() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/projects")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].projectName").exists())
                .andExpect(jsonPath("$[0].introduce").exists())
                .andExpect(jsonPath("$[0].descriptions").exists())
                .andReturn();

        List<Project> allProjectList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertFalse(CollectionUtils.isEmpty(allProjectList), "allProjectList is not empty");
    }


}
