package org.lala.profile.projects.web;

import org.lala.profile.projects.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/projects", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class ProjectController {

    private ProjectRepository projectRepository;

    private ModelMapper modelMapper;

    public ProjectController(ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity getAllProjects() {
        return ResponseEntity.ok(projectRepository.findAll());
    }
}
