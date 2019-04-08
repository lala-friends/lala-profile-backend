package org.lala.profile.projects.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.lala.profile.accounts.config.AccountSerializer;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.commons.AbstractTimestampEntity;

import javax.persistence.*;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Project extends AbstractTimestampEntity {

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

    @ManyToOne
    @JsonSerialize(using = AccountSerializer.class)
    private Account owner;
}
