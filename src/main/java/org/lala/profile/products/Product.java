package org.lala.profile.products;


import lombok.*;

import javax.persistence.*;

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

    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false, unique = true)
    private ProductDetail productDetail;
}
