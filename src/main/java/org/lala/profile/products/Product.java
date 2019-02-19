package org.lala.profile.products;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")   // for entity reference
public class Product {

    private Integer id;
    private String name;
    private String introduce;
    private String[] tech;
    private String imageUrl;
}
