package org.lala.profile.person.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lala.profile.projects.vo.Project;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonWithProjects extends Person {

    List<Project> projects;
}
