package org.lala.profile.products;

import lombok.*;
import org.lala.profile.commons.AbstractTimestampEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)   // for entity reference
@Entity
public class ProductDetail extends AbstractTimestampEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String description;
    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
