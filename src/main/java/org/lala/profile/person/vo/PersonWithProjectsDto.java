package org.lala.profile.person.vo;

import lombok.*;
import org.lala.profile.projects.vo.Project;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonWithProjectsDto {

    @NotEmpty
    private String name;
    private String introduce;
    private String imageUrl;
    private String repColor;
    private String blog;
    private String github;
    private String facebook;
    private String[] keywords;

    private List<Project> projects;
}
