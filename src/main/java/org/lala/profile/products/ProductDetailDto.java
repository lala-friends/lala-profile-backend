package org.lala.profile.products;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    private String imageUrl;

}
