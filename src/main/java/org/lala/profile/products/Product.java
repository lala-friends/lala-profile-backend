package org.lala.profile.products;

import lombok.*;
import org.lala.profile.commons.AbstractTimestampEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)   // for entity reference
@Entity
public class Product extends AbstractTimestampEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String introduce;
    private String[] tech;
    private String imageUrl;
    private String color;
}
