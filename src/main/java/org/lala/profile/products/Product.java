package org.lala.profile.products;

import lombok.*;
import org.lala.profile.accounts.Account;
import org.lala.profile.commons.AbstractTimestampEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product extends AbstractTimestampEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String introduce;
    private String descriptions;
    private String[] techs;
    private String[] imageUrls;
    private String color;

    @ManyToOne
    private Account owner;
}
