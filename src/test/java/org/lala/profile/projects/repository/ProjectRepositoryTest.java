package org.lala.profile.projects.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.accounts.repository.AccountsRepository;
import org.lala.profile.accounts.service.AccountsService;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.commons.AppProperties;
import org.lala.profile.projects.vo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectRepositoryTest extends AbstractCommonTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AppProperties appProperties;

    @Test
    @DisplayName("Project Repository 저장 테스트")
    void project_repository_save_test() {
        String admin = appProperties.getAdminUsername();
        Account byEmail = accountsRepository.findByEmail(admin).orElseThrow(() -> new UsernameNotFoundException(admin));

        Calendar startDt = new GregorianCalendar(2018, 0, 1);
        Calendar endDt = new GregorianCalendar(2018, 11, 31);

        Project project = Project.builder()
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

        Project savedProject = projectRepository.save(project);

        assertNotNull(savedProject);
        assertEquals("SI project1", savedProject.getProjectName());
        assertEquals(admin, savedProject.getOwner().getEmail());
    }
}
