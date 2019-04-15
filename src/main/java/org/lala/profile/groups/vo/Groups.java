package org.lala.profile.groups.vo;

import lombok.*;
import org.lala.profile.person.vo.Person;
import org.lala.profile.products.vo.Product;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Groups {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private boolean isProductOn;
}
