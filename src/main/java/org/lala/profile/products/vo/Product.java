package org.lala.profile.products.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.lala.profile.accounts.vo.Account;
import org.lala.profile.accounts.config.AccountSerializer;
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
    @JsonSerialize(using = AccountSerializer.class)
    private Account owner;
}
