package org.lala.profile.products;

import lombok.*;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {

    @Id
    private Integer id;
    private String title;
    private String description;
    private String imageUrl;

    private Integer productId;
}
