package org.lala.profile.projects.vo;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(JUnitParamsRunner.class)
public class ProjectVoTest {

    @Test
    @DisplayName("Project builder test")
    public void builderTest() {
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

        assertNotNull(project);
    }
}
