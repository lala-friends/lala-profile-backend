package org.lala.profile.projects.vo;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Project {

    @Id
    @GeneratedValue
    private Integer id;

    private String projectName;

    private Date periodFrom;
    private Date periodTo;

    private String introduce;

    @Column(columnDefinition = "TEXT")
    private String descriptions;

    private String[] techs;

    private String personalRole;

    private String link;
}
