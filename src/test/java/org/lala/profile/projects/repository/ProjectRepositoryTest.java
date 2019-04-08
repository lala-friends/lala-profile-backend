package org.lala.profile.projects.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lala.profile.projects.vo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;


    @Test
    @DisplayName("Project Repository 저장 테스트")
    void project_repository_save_test() {
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
                .build();

        Project savedProject = projectRepository.save(project);

        assertNotNull(savedProject);
        assertEquals("SI project1", savedProject.getProjectName());
    }
}
