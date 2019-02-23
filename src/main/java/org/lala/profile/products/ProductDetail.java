package org.lala.profile.products;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {

    private Integer id;
    private String title;
    private String description;
    private String imageUrl;

    private Integer productId;
}
