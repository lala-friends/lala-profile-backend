package org.lala.profile.projects.web;

import org.junit.jupiter.api.BeforeEach;
import org.lala.profile.common.AbstractCommonTest;
import org.lala.profile.projects.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectControllerTest extends AbstractCommonTest {

    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    void before() {

    }
}
