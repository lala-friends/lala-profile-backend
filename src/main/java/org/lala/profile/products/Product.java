package org.lala.profile.products;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")   // for entity reference
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String introduce;
    private String[] tech;
    private String imageUrl;
}
