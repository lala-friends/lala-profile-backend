package org.lala.profile.products.vo;

import lombok.*;
import org.lala.profile.commons.AbstractTimestampEntity;
import org.lala.profile.person.vo.Person;

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
    private String repImageUrl;
    private String description;
    private String[] techs;
    private String[] imageUrls;
    private String color;

//    @ManyToOne
//    @JsonSerialize(using = AccountSerializer.class)
//    private Account owner;

    @ManyToOne
    private Person owner;
}
