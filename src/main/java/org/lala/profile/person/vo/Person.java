package org.lala.profile.person.vo;

import lombok.*;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.commons.AbstractTimestampEntity;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Person extends AbstractTimestampEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String email;
    private String introduce;
    private String imageUrl;
    private String repColor;
    private String blog;
    private String github;
    private String facebook;
    private String[] keywords;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
